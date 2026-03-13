// Abstract Room class - defines common structure for all room types
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

    // Common behavior for all rooms
    public void displayDetails() {
        System.out.printf("%s Room: %d bed(s), %.0f sq ft, $%.2f/night%n",
                roomType, numberOfBeds, sizeInSqFt, pricePerNight);
    }

    // Getters for encapsulation
    public String getRoomType() { return roomType; }
    public int getNumberOfBeds() { return numberOfBeds; }
    public double getSizeInSqFt() { return sizeInSqFt; }
    public double getPricePerNight() { return pricePerNight; }

    // Abstract method - forces concrete classes to implement
    public abstract String getDescription();
}

// Concrete SingleRoom class
class SingleRoom extends Room {
    public SingleRoom() {
        super("Single", 1, 200, 120.00);
    }

    @Override
    public String getDescription() {
        return "Cozy room perfect for solo travelers";
    }
}

// Concrete DoubleRoom class
class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double", 2, 350, 180.00);
    }

    @Override
    public String getDescription() {
        return "Spacious room ideal for couples or friends";
    }
}

// Concrete SuiteRoom class
class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite", 1, 600, 350.00);
    }

    @Override
    public String getDescription() {
        return "Luxury suite with living area and premium amenities";
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        System.out.println("=== Hotel Room Availability System ===\n");

        // Create room objects using polymorphism
        Room singleRoom = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suiteRoom = new SuiteRoom();

        // Static availability representation (simple variables - shows limitations)
        boolean singleAvailable = true;
        boolean doubleAvailable = false;
        boolean suiteAvailable = true;

        // Display all rooms uniformly (polymorphism in action)
        System.out.println("Available Rooms:");
        displayRoomStatus(singleRoom, singleAvailable);
        displayRoomStatus(doubleRoom, doubleAvailable);
        displayRoomStatus(suiteRoom, suiteAvailable);

        System.out.println("\n=== End of Availability Check ===");
    }

    // Uniform handling of different room types
    private static void displayRoomStatus(Room room, boolean isAvailable) {
        room.displayDetails();
        System.out.printf("Description: %s%n", room.getDescription());
        System.out.printf("Status: %s%n%n",
                isAvailable ? "✅ AVAILABLE" : "❌ BOOKED");
    }
}

