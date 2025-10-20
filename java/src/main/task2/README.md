You have a list of Orders (List<Order>) received by the online store.
You need to analyze this list using StreamAPI to collect different business metrics
Data Structure:
class Order {
private String orderId;
private LocalDateTime orderDate;
private Customer customer;
private List<OrderItem> items;
private OrderStatus status;
}

class OrderItem {
private String productName;
private int quantity;
private double price;
private Category category;
}

class Customer {
private String customerId;
private String name;
private String email;
private LocalDateTime registeredAt;
private int age;
private String city;
}

enum OrderStatus {
NEW, PROCESSING, SHIPPED, DELIVERED, CANCELLED
}

enum Category {
ELECTRONICS, CLOTHING, BOOKS, HOME, BEAUTY, TOYS
}
Metrics
List of unique cities where orders came from
Total income for all completed orders
The most popular product by sales
Average check for successfully delivered orders
Customers who have more than 5 orders

All metrics should be covered with unit tests using JUnit 5