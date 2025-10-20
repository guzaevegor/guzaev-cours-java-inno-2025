package task4;

import task4.Component;
import task4.Scope;

@Component
@Scope("prototype")
public class PrototypeBean {
  private String value;


  public void setValue(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

}
