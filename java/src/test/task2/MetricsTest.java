package task2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class MetricsTest {

  private Metrics metrics;
  private Customer customer1;
  private Customer customer2;
  private Customer customer3;

  @BeforeEach
  void setUp() {
    metrics = new Metrics();

    // test data
    customer1 = new Customer(
        "1",
        "Иван Петров",
        "[email protected]",
        LocalDateTime.of(2023, 1, 15, 10, 0),
        28,
        "Минск"
    );

    customer2 = new Customer(
        "2",
        "Анна Сидорова",
        "[email protected]",
        LocalDateTime.of(2023, 3, 20, 14, 30),
        32,
        "Москва"
    );

    customer3 = new Customer(
        "3",
        "Петр Иванов",
        "[email protected]",
        LocalDateTime.of(2024, 5, 10, 9, 15),
        25,
        "Минск"
    );
  }

  @Test
  void testGetUniqueCitiesFromOrders() {
    // Given
    Order order1 = new Order(
        "O1",
        LocalDateTime.now(),
        customer1,
        List.of(),
        OrderStatus.DELIVERED
    );
    Order order2 = new Order(
        "O2",
        LocalDateTime.now(),
        customer2,
        List.of(),
        OrderStatus.DELIVERED
    );
    Order order3 = new Order(
        "O3",
        LocalDateTime.now(),
        customer3,
        List.of(),
        OrderStatus.DELIVERED
    );

    List<Order> orders = List.of(order1, order2, order3);

    // When
    Set<String> cities = metrics.getUniqueCitiesFromOrders(orders);

    // Then
    assertEquals(2, cities.size());
    assertTrue(cities.contains("Минск"));
    assertTrue(cities.contains("Москва"));
  }

  @Test
  void testGetTotalCompletedOrders() {
    // Given
    OrderItem item1 = new OrderItem("Ноутбук", 2, 1000.0, Category.ELECTRONICS);
    OrderItem item2 = new OrderItem("Мышь", 3, 20.0, Category.ELECTRONICS);
    OrderItem item3 = new OrderItem("Книга", 1, 15.0, Category.BOOKS);

    Order delivered1 = new Order(
        "O1",
        LocalDateTime.now(),
        customer1,
        List.of(item1, item2),
        OrderStatus.DELIVERED
    );
    Order delivered2 = new Order(
        "O2",
        LocalDateTime.now(),
        customer2,
        List.of(item3),
        OrderStatus.DELIVERED
    );
    Order cancelled = new Order(
        "O3",
        LocalDateTime.now(),
        customer3,
        List.of(new OrderItem("Футболка", 5, 50.0, Category.CLOTHING)),
        OrderStatus.CANCELLED
    );

    List<Order> orders = List.of(delivered1, delivered2, cancelled);

    // When
    double totalRevenue = metrics.getTotalCompletedOrders(orders);

    // Then
    assertEquals(2075.0, totalRevenue, 0.01); // 2*1000 + 3*20 + 1*15 = 2075
  }

  @Test
  void testGetMostPopularProductBySales() {
    // Given
    OrderItem item1 = new OrderItem("Мышь Logitech", 5, 30.0, Category.ELECTRONICS);
    OrderItem item2 = new OrderItem("Ноутбук", 2, 1500.0, Category.ELECTRONICS);
    OrderItem item3 = new OrderItem("Мышь Logitech", 8, 30.0, Category.ELECTRONICS);
    OrderItem item4 = new OrderItem("Ноутбук", 3, 1500.0, Category.ELECTRONICS);

    Order order1 = new Order(
        "O1",
        LocalDateTime.now(),
        customer1,
        List.of(item1, item2),
        OrderStatus.DELIVERED
    );
    Order order2 = new Order(
        "O2",
        LocalDateTime.now(),
        customer2,
        List.of(item3, item4),
        OrderStatus.DELIVERED
    );

    List<Order> orders = List.of(order1, order2);

    // When
    Optional<String> mostPopular = metrics.getMostPopularProductBySales(orders);

    // Then
    assertTrue(mostPopular.isPresent());
    assertEquals("Мышь Logitech", mostPopular.get()); // 5+8=13 > 2+3=5
  }

  @Test
  void testGetAverageValueDeliveredOrder() {
    // Given
    OrderItem item1 = new OrderItem("Ноутбук", 1, 1000.0, Category.ELECTRONICS);
    OrderItem item2 = new OrderItem("Мышь", 2, 50.0, Category.ELECTRONICS);
    OrderItem item3 = new OrderItem("Книга", 1, 200.0, Category.BOOKS);

    Order order1 = new Order(
        "O1",
        LocalDateTime.now(),
        customer1,
        List.of(item1),
        OrderStatus.DELIVERED
    );

    Order order2 = new Order(
        "O2",
        LocalDateTime.now(),
        customer2,
        List.of(item2),
        OrderStatus.DELIVERED
    );

    Order order3 = new Order(
        "O3",
        LocalDateTime.now(),
        customer3,
        List.of(item3),
        OrderStatus.CANCELLED
    );

    List<Order> orders = List.of(order1, order2, order3);

    // When
    double average = metrics.getAverageValueDeliveredOrder(orders);

    // Then
    assertEquals(550.0, average, 0.01); // (1000 + 100) / 2 = 550
  }

  @Test
  void testGetCustomersWithMoreThanFiveOrders() {
    // Given
    List<Order> orders = new ArrayList<>();


    for (int i = 0; i < 6; i++) {
      orders.add(new Order(
          "O" + i,
          LocalDateTime.now(),
          customer1,
          List.of(new OrderItem("Товар" + i, 1, 100.0, Category.HOME)),
          OrderStatus.DELIVERED
      ));
    }


    for (int i = 6; i < 9; i++) {
      orders.add(new Order(
          "O" + i,
          LocalDateTime.now(),
          customer2,
          List.of(new OrderItem("Товар" + i, 1, 100.0, Category.HOME)),
          OrderStatus.DELIVERED
      ));
    }


    for (int i = 9; i < 14; i++) {
      orders.add(new Order(
          "O" + i,
          LocalDateTime.now(),
          customer3,
          List.of(new OrderItem("Товар" + i, 1, 100.0, Category.HOME)),
          OrderStatus.DELIVERED
      ));
    }

    // When
    List<Customer> activeCustomers = metrics.getCustomersWithMoreThanFiveOrders(orders);

    // Then
    assertEquals(1, activeCustomers.size());
    assertTrue(activeCustomers.contains(customer1));
    assertFalse(activeCustomers.contains(customer2));
    assertFalse(activeCustomers.contains(customer3));
  }

}
