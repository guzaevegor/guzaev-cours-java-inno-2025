package task2;


import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class Metrics {


  public Set<String> getUniqueCitiesFromOrders(List<Order> orders) {
    return orders.stream()
        .map(order -> order.getCustomer().getCity())
        .collect(Collectors.toSet());
  }


  public double getTotalCompletedOrders(List<Order> orders) {
    return orders.stream()
        .filter(order -> order.getStatus() == OrderStatus.DELIVERED)
        .flatMap(order -> order.getItems().stream())
        .mapToDouble(item -> item.getPrice() * item.getQuantity())
        .sum();
  }


  public Optional<String> getMostPopularProductBySales(List<Order> orders) {
    return orders.stream()
        .filter(order -> order.getStatus() == OrderStatus.DELIVERED)
        .flatMap(order -> order.getItems().stream())
        .collect(Collectors.groupingBy(
            OrderItem::getProductName,
            Collectors.summingInt(OrderItem::getQuantity)
        ))
        .entrySet().stream()
        .max(Map.Entry.comparingByValue())
        .map(Map.Entry::getKey);
  }

  public double getAverageValueDeliveredOrder(List<Order> orders) {
    return orders.stream()
        .filter(order -> order.getStatus() == OrderStatus.DELIVERED)
        .mapToDouble(order -> order.getItems().stream()
            .mapToDouble(item -> item.getPrice() * item.getQuantity())
            .sum())
        .average()
        .orElse(0.0);
  }


  public List<Customer> getCustomersWithMoreThanFiveOrders(List<Order> orders) {
    return orders.stream()
        .collect(Collectors.groupingBy(
            Order::getCustomer,
            Collectors.counting()
        ))
        .entrySet().stream()
        .filter(entry -> entry.getValue() > 5)
        .map(Map.Entry::getKey)
        .collect(Collectors.toList());
  }
}
