package task4;

import task4.Autowired;
import task4.Component;
import task4.InitializingBean;

@Component
public class TestService implements InitializingBean {

  @Autowired
  private TestRepository repository;

  private boolean initialized = false;

  @Override
  public void afterPropertiesSet() throws Exception {
    this.initialized = true;
  }

  public TestRepository getRepository() {
    return repository;
  }

  public boolean isInitialized() {
    return initialized;
  }
}
