import java.util.*;

// ENUMS
enum VehicleType {
    CAR, TRUCK, BUS
}

enum PaymentType {
    FASTAG, CASH
}

// VEHICLE
class Vehicle {
    private String number;
    private VehicleType type;

    public Vehicle(String number, VehicleType type) {
        this.number = number;
        this.type = type;
    }

    public String getNumber() { return number; }
    public VehicleType getType() { return type; }
}

// ABSTRACT PAYMENT
abstract class Payment {
    protected int amount;

    public Payment(int amount) {
        this.amount = amount;
    }

    abstract boolean pay();
}

// FASTAG PAYMENT
class FastagPayment extends Payment {
    private int balance;

    public FastagPayment(int amount, int balance) {
        super(amount);
        this.balance = balance;
    }

    @Override
    public boolean pay() {
        if (balance >= amount) {
            balance -= amount;
            System.out.println("FASTag Payment Successful. Remaining Balance: " + balance);
            return true;
        } else {
            System.out.println("Insufficient FASTag Balance!");
            return false;
        }
    }
}

// CASH PAYMENT
class CashPayment extends Payment {

    public CashPayment(int amount) {
        super(amount);
    }

    @Override
    public boolean pay() {
        System.out.println("Cash Payment Successful: " + amount);
        return true;
    }
}

// TOLL PLAZA
class TollPlaza {
    private String name;

    public TollPlaza(String name) {
        this.name = name;
    }

    public int calculateFee(VehicleType type) {
        switch (type) {
            case CAR: return 50;
            case TRUCK: return 100;
            case BUS: return 80;
            default: return 60;
        }
    }

    public String getName() { return name; }
}

// TRANSACTION
class Transaction {
    private static int counter = 1;
    private int id;
    private Vehicle vehicle;
    private int amount;

    public Transaction(Vehicle vehicle, int amount) {
        this.id = counter++;
        this.vehicle = vehicle;
        this.amount = amount;
    }

    public void print() {
        System.out.println("\n--- TRANSACTION DETAILS ---");
        System.out.println("Transaction ID: " + id);
        System.out.println("Vehicle Number: " + vehicle.getNumber());
        System.out.println("Vehicle Type: " + vehicle.getType());
        System.out.println("Amount Paid: " + amount);
    }
}

// MAIN APP
public class TollPlazaApp {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // VEHICLE INPUT
        System.out.print("Enter Vehicle Number: ");
        String number = sc.nextLine();

        System.out.print("Enter Vehicle Type (CAR/TRUCK/BUS): ");
        VehicleType type = VehicleType.valueOf(sc.nextLine().toUpperCase());

        Vehicle vehicle = new Vehicle(number, type);

        // TOLL
        TollPlaza toll = new TollPlaza("NH Toll Plaza");

        int fee = toll.calculateFee(type);
        System.out.println("Toll Fee: " + fee);

        // PAYMENT
        System.out.println("Choose Payment: 1. FASTag  2. CASH");
        int choice = sc.nextInt();

        Payment payment;

        if (choice == 1) {
            System.out.print("Enter FASTag Balance: ");
            int balance = sc.nextInt();
            payment = new FastagPayment(fee, balance);
        } else {
            payment = new CashPayment(fee);
        }

        // PROCESS PAYMENT
        if (payment.pay()) {
            Transaction t = new Transaction(vehicle, fee);
            t.print();
        } else {
            System.out.println("Transaction Failed!");
        }

        sc.close();
    }
}
