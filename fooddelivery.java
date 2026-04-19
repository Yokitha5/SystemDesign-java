import java.util.*;
import java.time.*;

// ENUMS
enum OrderStatus {
    PLACED, PREPARING, DELIVERED
}

enum PaymentMethod {
    CASH, CARD, UPI
}

// ABSTRACT PAYMENT
abstract class Payment {
    protected int paymentId;
    protected double amount;

    Payment(int paymentId, double amount) {
        this.paymentId = paymentId;
        this.amount = amount;
    }

    abstract void pay();
}

// USER
class User {
    private int id;
    private String name, phone, email, address;

    public User(int id, String name, String phone, String email, String address) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
}

// RESTAURANT
class Restaurant {
    private int id;
    private String name, address, phone;

    private LocalTime openTime = LocalTime.of(8, 0);
    private LocalTime closeTime = LocalTime.of(22, 0);

    public Restaurant(int id, String name, String address, String phone) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    public String getName() { return name; }

    public boolean isOpen() {
        LocalTime now = LocalTime.now();
        return !(now.isBefore(openTime) || now.isAfter(closeTime));
    }
}

// MENU
class MenuItem {
    private int id;
    private String name, type;
    private double price;

    public MenuItem(int id, String name, String type, double price) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.price = price;
    }

    public double getPrice() { return price; }
    public String getName() { return name; }
}

// ORDER
class Order {
    private int id;
    private MenuItem item;
    private Restaurant restaurant;
    private int quantity;
    private double totalPrice;
    private OrderStatus status;
    private Instant time;

    public Order(int id, MenuItem item, Restaurant restaurant, int quantity) {
        this.id = id;
        this.item = item;
        this.restaurant = restaurant;
        this.quantity = quantity;
        this.totalPrice = item.getPrice() * quantity;
        this.status = OrderStatus.PLACED;
        this.time = Instant.now();
    }

    public int getId() { return id; }
    public double getTotalPrice() { return totalPrice; }

    public void printDetails() {
        System.out.println("\n--- ORDER DETAILS ---");
        System.out.println("Order ID: " + id);
        System.out.println("Item: " + item.getName());
        System.out.println("Restaurant: " + restaurant.getName());
        System.out.println("Quantity: " + quantity);
        System.out.println("Total: " + totalPrice);
        System.out.println("Status: " + status);
        System.out.println("Time: " + time);
    }
}

// PAYMENT IMPLEMENTATION
class SimplePayment extends Payment {
    private PaymentMethod method;
    private Order order;

    public SimplePayment(int id, double amount, PaymentMethod method, Order order) {
        super(id, amount);
        this.method = method;
        this.order = order;
    }

    @Override
    public void pay() {
        System.out.println("\n--- PAYMENT ---");
        System.out.println("Order ID: " + order.getId());
        System.out.println("Amount: " + amount);
        System.out.println("Method: " + method);
        System.out.println("Status: SUCCESS");
    }
}

// DELIVERY
class DeliveryPartner {
    private int id;
    private String name, phone;
    private Order order;
    private User user;

    public DeliveryPartner(int id, String name, String phone, User user, Order order) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.user = user;
        this.order = order;
    }

    public void deliver() {
        System.out.println("\n--- DELIVERY ---");
        System.out.println("Partner: " + name);
        System.out.println("Delivering to: " + user.getName());
        System.out.println("Address: " + user.getAddress());
        System.out.println("Status: ON THE WAY");
    }
}

// MAIN APP
public class FoodDeliveryApp {
    private static int idCounter = 1;

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // USER INPUT
        System.out.print("Enter Name: ");
        User user = new User(idCounter++, sc.nextLine(),
                sc.nextLine(), sc.nextLine(), sc.nextLine());

        // RESTAURANT
        System.out.print("Enter Restaurant Name: ");
        Restaurant res = new Restaurant(idCounter++, sc.nextLine(),
                sc.nextLine(), sc.nextLine());

        if (!res.isOpen()) {
            System.out.println("Restaurant is closed!");
            return;
        }

        // MENU
        System.out.print("Enter Item Name: ");
        String itemName = sc.nextLine();
        System.out.print("Enter Type: ");
        String type = sc.nextLine();
        System.out.print("Enter Price: ");
        double price = sc.nextDouble();

        MenuItem item = new MenuItem(idCounter++, itemName, type, price);

        // ORDER
        System.out.print("Enter Quantity: ");
        int qty = sc.nextInt();

        Order order = new Order(idCounter++, item, res, qty);
        order.printDetails();

        // PAYMENT
        sc.nextLine();
        System.out.print("Enter Payment Method (CASH/UPI/CARD): ");
        PaymentMethod method = PaymentMethod.valueOf(sc.nextLine().toUpperCase());

        Payment payment = new SimplePayment(idCounter++, order.getTotalPrice(), method, order);
        payment.pay();

        // DELIVERY
        DeliveryPartner dp = new DeliveryPartner(idCounter++, "Rider1", "9999999999", user, order);
        dp.deliver();

        sc.close();
    }
}
