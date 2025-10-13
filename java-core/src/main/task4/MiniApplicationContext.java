package task4;

import java.util.Map;

public class MiniApplicationContext {
  private Map<Class<?>, Object> singletonBeans;
  private Map<Class<?>, Class<?>> beanDefinitions;

  public MiniApplicationContext(String packageName) throws Exception {
  }

  private void scanPackage(String packageName) throws Exception {
  }

  private void instantiateBeans() throws Exception {
  }

  private void injectDependencies() throws Exception {
  }

  private void callInitMethods() throws Exception {
  }

  public <T> T getBean(Class<T> type) {
    return null;
  }
}
