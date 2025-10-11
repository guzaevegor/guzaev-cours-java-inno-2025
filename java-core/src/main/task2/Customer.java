package task2;

import java.time.LocalDateTime;

public class Customer {
  private String customerId;
  private String name;
  private String email;
  private LocalDateTime registeredAt; //why not a unix type?
  private int age;
  private String city;
}