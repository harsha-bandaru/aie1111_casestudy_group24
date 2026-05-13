import java.util.Scanner;
import java.util.Arrays;

class User {
    protected int id;
    protected String name;

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }
}

class Customer extends User {
    private String address;

    public Customer(int customerID, String customername, String address) {
        super(customerID, customername);
        this.address = address;
    }

    public void sendParcel() {
        System.out.println("Customer " + name + " is sending a parcel from: " + address);
    }

    public void trackParcel(int trackingID, CourierManagementSystem system) {
        System.out.println("Customer " + name + " is tracking parcel ID: " + trackingID);
        system.viewRecords(trackingID);
    }
}

class CourierAgent extends User {
    private String currentLocation;

    public CourierAgent(int agentID, String agentname, String currentLocation) {
        super(agentID, agentname);
        this.currentLocation = currentLocation;
    }

    public void deliverParcel() {
        System.out.println("Agent " + name + " is delivering the parcel at " + currentLocation);
    }

    public void updateStatus(int trackingID, String newStatus, CourierManagementSystem system) {
        System.out.println("Agent " + name + " updated status for ID " + trackingID + " to: " + newStatus);
        system.updateSystemLogs(trackingID, newStatus);
    }
}

class Admin extends User {
    private int accessLevel;

    public Admin(int adminID, int accessLevel) {
        super(adminID, "Admin User"); 
        this.accessLevel = accessLevel;
    }

    public void manageOperations() {
        System.out.println("Admin ID " + id + " with Access Level " + accessLevel + " is managing operations.");
    }

    public void viewRecords(CourierManagementSystem system) {
        System.out.println("Admin viewing all delivery logs...");
        system.displayAllLogs();
    }
}

class CourierManagementSystem {
    private int[] trackingIDs;
    private String[] currentstatusarray;
    private int totalParcels;
    private int capacity = 10;

    public CourierManagementSystem() {
        this.trackingIDs = new int[capacity];
        this.currentstatusarray = new String[capacity];
        this.totalParcels = 0;
    }

    public void registerParcel(int trackingID) {
        if (totalParcels < capacity) {
            trackingIDs[totalParcels] = trackingID;
            currentstatusarray[totalParcels] = "Registered";
            totalParcels++;
            System.out.println("System: Parcel " + trackingID + " registered successfully.");
        }
    }

    public void updateSystemLogs(int trackingID, String status) {
        for (int i = 0; i < totalParcels; i++) {
            if (trackingIDs[i] == trackingID) {
                currentstatusarray[i] = status;
                return;
            }
        }
    }

    public void viewRecords(int trackingID) {
        for (int i = 0; i < totalParcels; i++) {
            if (trackingIDs[i] == trackingID) {
                System.out.println("Status of Parcel " + trackingID + ": " + currentstatusarray[i]);
                return;
            }
        }
        System.out.println("Tracking ID not found.");
    }

    public void displayAllLogs() {
        for (int i = 0; i < totalParcels; i++) {
            System.out.println("ID: " + trackingIDs[i] + " | Status: " + currentstatusarray[i]);
        }
    }
}

public class CourierApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CourierManagementSystem cms = new CourierManagementSystem();

        Admin admin = new Admin(101, 5); // 
        Customer predefinedCustomer = new Customer(25106, "B. HARSHA", "Kerala Campus"); // 
        CourierAgent agent = new CourierAgent(501, "Agent X", "Sorting Center"); // 

        System.out.println("Predefined Data Execution");
        cms.registerParcel(999);
        predefinedCustomer.sendParcel();
        agent.updateStatus(999, "In Transit", cms);
        admin.manageOperations();
        cms.viewRecords(999);

    
        System.out.println("\n-User Input Session ");
        System.out.print("Enter your Name (Customer): ");
        String name = scanner.nextLine();
        System.out.print("Enter your ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); 
        System.out.print("Enter your Address: ");
        String addr = scanner.nextLine();

        Customer userCustomer = new Customer(id, name, addr);
        
        System.out.print("Enter a new Tracking ID to register: ");
        int newTrackID = scanner.nextInt();
        
        cms.registerParcel(newTrackID);
        userCustomer.sendParcel();
        
        System.out.println("\nFinal System Status");
        admin.viewRecords(cms);

        scanner.close();
    }
}