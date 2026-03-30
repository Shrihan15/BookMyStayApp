import java.util.HashMap;
import java.util.Map;

// Room domain model remains unchanged from Use Case 2
abstract class Room {
    protected String roomType;
    protected int numberOfBeds;
    protected double sizeInSqFt;
    protected double pricePerNight;

    public Room(String roomType, int numberOfBeds, double sizeInSqFt, double pricePerNight) {
        this.roomType = roomType;
        this.numberOfBeds = numberOfBeds;
        this.sizeInSqFt = sizeInSqFt;
        this.pricePerNight = pricePerNight;
    }

    public void displayDetails() {
        System.out.printf("%s Room: %d bed(s), %.0f sq ft, $%.2f/night%n",
                roomType, numberOfBeds, sizeInSqFt, pricePerNight);
    }

    public String getRoomType() { return roomType; }
    public String getDescription() { return ""; }
}

class SingleRoom extends Room {
    public SingleRoom() { super("Single", 1, 200, 120.00); }
    public String getDescription() { return "Cozy room perfect for solo travelers"; }
}

class DoubleRoom extends Room {
    public DoubleRoom() { super("Double", 2, 350, 180.00); }
    public String getDescription() { return "Spacious room ideal for couples or friends"; }
}

class SuiteRoom extends Room {
    public SuiteRoom() { super("Suite", 1, 600, 350.00); }
    public String getDescription() { return "Luxury suite with premium amenities"; }
}

// Centralized RoomInventory - single source of truth for availability
class RoomInventory {
    private Map<String, Integer> availableRooms;

    public RoomInventory() {
        this.availableRooms = new HashMap<>();
        initializeInventory();
    }

    // Initialize with predefined inventory (O(1) inserts)
    private void initializeInventory() {
        availableRooms.put("Single", 5);
        availableRooms.put("Double", 3);
        availableRooms.put("Suite", 2);
    }

    // O(1) lookup - check availability for room type
    public int getAvailableCount(String roomType) {
        return availableRooms.getOrDefault(roomType, 0);
    }

    // O(1) update - book a room (controlled inventory update)
    public boolean bookRoom(String roomType) {
        int currentCount = getAvailableCount(roomType);
        if (currentCount > 0) {
            availableRooms.put(roomType, currentCount - 1);
            return true;
        }
        return false;
    }

    // O(1) update - add rooms to inventory
    public void addRooms(String roomType, int count) {
        int currentCount = getAvailableCount(roomType);
        availableRooms.put(roomType, currentCount + count);
    }

    // Display complete inventory state
    public void displayInventory() {
        System.out.println("\n=== Current Room Inventory ===");
        for (String roomType : availableRooms.keySet()) {
            int count = availableRooms.get(roomType);
            System.out.printf("%s: %d available %s%n",
                    roomType, count, count == 1 ? "room" : "rooms");
        }
    }

    // Get total available rooms across all types
    public int getTotalAvailableRooms() {
        return availableRooms.values().stream().mapToInt(Integer::intValue).sum();
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        System.out.println("=== Hotel Room Inventory Management System ===\n");

        // Initialize centralized inventory (single source of truth)
        RoomInventory inventory = new RoomInventory();

        // Create room objects for display (domain model unchanged)
        Room singleRoom = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suiteRoom = new SuiteRoom();

        // Display initial inventory
        inventory.displayInventory();

        // Demonstrate booking operations (O(1) updates)
        System.out.println("\n--- Booking Operations ---");
        System.out.println("Booking 1 Single room: " +
                (inventory.bookRoom("Single") ? "SUCCESS" : "FAILED"));
        System.out.println("Booking 1 Double room: " +
                (inventory.bookRoom("Double") ? "SUCCESS" : "FAILED"));
        System.out.println("Booking 1 Suite room: " +
                (inventory.bookRoom("Suite") ? "SUCCESS" : "FAILED"));

        // Display updated inventory
        inventory.displayInventory();

        // Demonstrate adding inventory
        System.out.println("\n--- Adding 2 Double rooms to inventory ---");
        inventory.addRooms("Double", 2);
        inventory.displayInventory();

        System.out.printf("\nTotal available rooms: %d%n",
                inventory.getTotalAvailableRooms());

        System.out.println("\n=== End of Inventory Demo ===");
    }
}


