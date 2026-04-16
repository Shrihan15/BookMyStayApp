import java.util.*;

// Reservation (Confirmed Booking)
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

// Add-On Service
class AddOnService {
    private String serviceName;
    private double cost;

    public AddOnService(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getCost() {
        return cost;
    }

    public void display() {
        System.out.println(serviceName + " - ₹" + cost);
    }
}

// Add-On Service Manager
class AddOnServiceManager {

    // Map: ReservationID -> List of Services
    private Map<String, List<AddOnService>> serviceMap = new HashMap<>();

    // Add service to a reservation
    public void addService(String reservationId, AddOnService service) {
        serviceMap
                .computeIfAbsent(reservationId, k -> new ArrayList<>())
                .add(service);

        System.out.println("Service added: " + service.getServiceName() +
                " for Reservation ID: " + reservationId);
    }

    // View services for a reservation
    public void viewServices(String reservationId) {
        System.out.println("\nServices for Reservation ID: " + reservationId);

        List<AddOnService> services = serviceMap.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No add-on services selected.");
            return;
        }

        for (AddOnService s : services) {
            s.display();
        }
    }

    // Calculate total additional cost
    public double calculateTotalCost(String reservationId) {
        List<AddOnService> services = serviceMap.get(reservationId);

        if (services == null) return 0;

        double total = 0;
        for (AddOnService s : services) {
            total += s.getCost();
        }
        return total;
    }
}

// Main Class (as requested)
public class BookMyStayApp {

    public static void main(String[] args) {

        // Step 1: Create a reservation (already confirmed)
        Reservation reservation = new Reservation("RES101", "Alice", "Deluxe");

        // Step 2: Create Add-On Services
        AddOnService breakfast = new AddOnService("Breakfast", 300);
        AddOnService airportPickup = new AddOnService("Airport Pickup", 800);
        AddOnService extraBed = new AddOnService("Extra Bed", 500);

        // Step 3: Add services
        AddOnServiceManager manager = new AddOnServiceManager();

        manager.addService(reservation.getReservationId(), breakfast);
        manager.addService(reservation.getReservationId(), airportPickup);
        manager.addService(reservation.getReservationId(), extraBed);

        // Step 4: View selected services
        manager.viewServices(reservation.getReservationId());

        // Step 5: Calculate total add-on cost
        double totalCost = manager.calculateTotalCost(reservation.getReservationId());

        System.out.println("\nTotal Add-On Cost: ₹" + totalCost);
    }
}