package task4;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class MiniApplicationContext {
  private Map<Class<?>, Object> singletonBeans;
  private Map<Class<?>, Class<?>> beanDefinitions;

  public MiniApplicationContext(String packageName) throws Exception {
    this.singletonBeans = new HashMap<>();
    this.beanDefinitions = new HashMap<>();

    // Последовательно выполняем все этапы инициализации контейнера
    scanPackage(packageName);
    instantiateBeans();
    injectDependencies();
    callInitMethods();
  }

  /**
   * Сканирует указанный пакет и находит все классы с аннотацией @Component
   */
  private void scanPackage(String packageName) throws Exception {
    // 1. Преобразуем имя пакета в путь: task4 -> task4/
    String path = packageName.replace('.', '/');

    // 2. Получаем ClassLoader для загрузки ресурсов
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

    // 3. Получаем URL директории с классами
    URL resource = classLoader.getResource(path);
    if (resource == null) {
      throw new RuntimeException("Package not found: " + packageName);
    }

    // 4. Создаем File объект для директории
    File directory = new File(resource.getFile());

    // 5. Проверяем, что это директория
    if (!directory.exists()) {
      return;
    }

    // 6. Получаем все файлы в директории
    File[] files = directory.listFiles();
    if (files == null) {
      return;
    }

    // 7. Проходим по всем файлам
    for (File file : files) {
      // 8. Обрабатываем только .class файлы
      if (file.getName().endsWith(".class")) {
        // 9. Получаем имя класса: MyClass.class -> MyClass
        String className = file.getName().substring(0, file.getName().length() - 6);

        // 10. Формируем полное имя класса: task4.MyClass
        String fullClassName = packageName + '.' + className;

        // 11. Загружаем класс через рефлексию
        Class<?> clazz = Class.forName(fullClassName);

        // 12. Проверяем, есть ли аннотация @Component
        if (clazz.isAnnotationPresent(Component.class)) {
          // 13. Сохраняем класс в beanDefinitions
          beanDefinitions.put(clazz, clazz);
        }
      }
    }
  }

  /**
   * Создает экземпляры всех singleton бинов
   */
  private void instantiateBeans() throws Exception {
    // 1. Проходим по всем найденным классам
    for (Class<?> clazz : beanDefinitions.keySet()) {
      // 2. Проверяем scope бина
      boolean isPrototype = false;

      if (clazz.isAnnotationPresent(Scope.class)) {
        Scope scopeAnnotation = clazz.getAnnotation(Scope.class);
        if ("prototype".equals(scopeAnnotation.value())) {
          isPrototype = true;
        }
      }

      // 3. Для singleton создаем экземпляр сразу
      if (!isPrototype) {
        // 4. Создаем объект через рефлексию
        Object instance = clazz.getDeclaredConstructor().newInstance();

        // 5. Сохраняем в singletonBeans
        singletonBeans.put(clazz, instance);
      }
      // Для prototype НЕ создаем объект сейчас, создадим в getBean()
    }
  }

  /**
   * Внедряет зависимости в поля с аннотацией @Autowired
   */
  private void injectDependencies() throws Exception {
    // 1. Проходим по всем созданным singleton бинам
    for (Object bean : singletonBeans.values()) {
      // 2. Внедряем зависимости в этот бин
      injectFields(bean);
    }
  }

  /**
   * Внедряет зависимости в конкретный объект
   */
  private void injectFields(Object instance) throws Exception {
    // 1. Получаем класс объекта
    Class<?> clazz = instance.getClass();

    // 2. Получаем все поля класса (включая private)
    Field[] fields = clazz.getDeclaredFields();

    // 3. Проходим по всем полям
    for (Field field : fields) {
      // 4. Проверяем, есть ли аннотация @Autowired
      if (field.isAnnotationPresent(Autowired.class)) {
        // 5. Получаем тип поля (какой класс нужен)
        Class<?> fieldType = field.getType();

        // 6. Ищем бин нужного типа
        Object dependency = singletonBeans.get(fieldType);

        // 7. Если не нашли singleton, проверяем prototype
        if (dependency == null && beanDefinitions.containsKey(fieldType)) {
          // Создаем новый экземпляр для prototype
          dependency = fieldType.getDeclaredConstructor().newInstance();
          // Внедряем зависимости в новый объект
          injectFields(dependency);
        }

        // 8. Делаем поле доступным (обходим private)
        field.setAccessible(true);

        // 9. Устанавливаем значение поля
        field.set(instance, dependency);
      }
    }
  }

  /**
   * Вызывает методы инициализации для бинов, реализующих InitializingBean
   */
  private void callInitMethods() throws Exception {
    // 1. Проходим по всем созданным singleton бинам
    for (Object bean : singletonBeans.values()) {
      // 2. Проверяем, реализует ли бин InitializingBean
      if (bean instanceof InitializingBean) {
        // 3. Приводим к типу InitializingBean
        InitializingBean initializingBean = (InitializingBean) bean;

        // 4. Вызываем метод afterPropertiesSet()
        initializingBean.afterPropertiesSet();
      }
    }
  }

  /**
   * Возвращает бин указанного типа
   */
  public <T> T getBean(Class<T> type) {
    // 1. Проверяем, есть ли готовый singleton
    if (singletonBeans.containsKey(type)) {
      // 2. Возвращаем существующий singleton
      return type.cast(singletonBeans.get(type));
    }

    // 3. Проверяем, зарегистрирован ли этот класс как бин
    if (!beanDefinitions.containsKey(type)) {
      return null; // Бин не найден
    }

    // 4. Проверяем scope бина
    Class<?> clazz = beanDefinitions.get(type);
    if (clazz.isAnnotationPresent(Scope.class)) {
      Scope scopeAnnotation = clazz.getAnnotation(Scope.class);

      // 5. Если prototype - создаем новый экземпляр
      if ("prototype".equals(scopeAnnotation.value())) {
        try {
          // 6. Создаем новый объект
          T instance = type.cast(clazz.getDeclaredConstructor().newInstance());

          // 7. Внедряем зависимости
          injectFields(instance);

          // 8. Вызываем afterPropertiesSet, если реализован
          if (instance instanceof InitializingBean) {
            ((InitializingBean) instance).afterPropertiesSet();
          }

          // 9. Возвращаем новый экземпляр
          return instance;
        } catch (Exception e) {
          throw new RuntimeException("Failed to create prototype bean", e);
        }
      }
    }

    // 10. Если не singleton и не prototype - возвращаем null
    return null;
  }
}
