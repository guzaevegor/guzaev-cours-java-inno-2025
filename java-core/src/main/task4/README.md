You need to implement a simplified version of the Spring Framework, called MiniSpring, to understand how IoC (Inversion of Control) and Dependency Injection work internally.
The goal is to build your own lightweight DI container, capable of scanning packages for annotated components, instantiating beans, and injecting dependencies automatically using Reflection.

IoC Container
Create MiniApplicationContext that:
Scans a given package for classes annotated with @Component.
Instantiates and stores them in a map (Map<Class<?>, Object>).
Provides method getBean(Class<T> type) to retrieve instances.
2️⃣ Custom Annotations
Implement annotations:

@Component
@Autowired
@Component marks a class as a bean.
@Autowired injects dependencies into fields.
3️⃣ Dependency Injection
When creating a bean, automatically inject dependencies marked with @Autowired (via Reflection).
4️⃣ Bean Lifecycle
Support InitializingBean interface with method afterPropertiesSet() — it should be called once dependencies are injected.
5️⃣ (Optional) Bean Scopes
Implement optional @Scope("prototype") annotation that creates new bean instances each time.
