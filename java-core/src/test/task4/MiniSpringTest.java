package task4;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class MiniSpringTest {

  private MiniApplicationContext context;

  @BeforeEach
  public void setUp() throws Exception {
    MiniApplicationContext context = new MiniApplicationContext("task4");
  }

  @Test
  public void testComponentScanning() {
    TestRepository repository = context.getBean(TestRepository.class);
    assertNotNull(repository, "Repository bean should be created");
    assertEquals("test data", repository.getData());
  }

  @Test
  public void testDependencyInjection() {
    TestService service = context.getBean(TestService.class);
    assertNotNull(service, "Service bean should be created");
    assertNotNull(service.getRepository(), "Repository should be injected into service");
    assertEquals("test data", service.getRepository().getData());
  }

  @Test
  public void testInitializingBeanLifecycle() {
    TestService service = context.getBean(TestService.class);
    assertTrue(service.isInitialized(),
        "afterPropertiesSet() should be called after dependency injection");
  }

  @Test
  public void testSingletonScope() {
    TestRepository repo1 = context.getBean(TestRepository.class);
    TestRepository repo2 = context.getBean(TestRepository.class);
    assertSame(repo1, repo2,
        "Singleton beans should return the same instance");
  }

  @Test
  public void testPrototypeScope() {
    PrototypeBean bean1 = context.getBean(PrototypeBean.class);
    PrototypeBean bean2 = context.getBean(PrototypeBean.class);

    bean1.setValue("first");
    bean2.setValue("second");

    assertNotSame(bean1, bean2,
        "Prototype beans should return different instances");
    assertEquals("first", bean1.getValue());
    assertEquals("second", bean2.getValue());
  }
}
