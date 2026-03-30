import java.util.*;
import java.util.stream.Collectors;

// --- 1. THE DOMAIN MODEL (Room Details) ---
class Room {
    private String id;
    private String type;
    private double pricePerNight;
    private List<String> amenities;

    public Room(String id, String type, double pricePerNight, List<String> amenities) {
        this.id = id;
        this.type = type;
        this.pricePerNight = pricePerNight;
        this.amenities = amenities;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return String.format(
                "%-15s | Price: $%-6.2f | Amenities: %s",
                type, pricePerNight, String.join(", ", amenities)
        );
    }
}

// --- 2. INVENTORY (State Holder) ---
class Inventory {
    private final Map<String, Integer> availability = new HashMap<>();

    public void setAvailability(String roomId, int count) {
        availability.put(roomId, count);
    }

    public int getCount(String roomId) {
        return availability.getOrDefault(roomId, 0);
    }
}

// --- 3. SEARCH SERVICE (Business Logic) ---
class SearchService {
    private final Inventory inventory;
    private final Map<String, Room> roomCatalog;

    public SearchService(Inventory inventory, Map<String, Room> roomCatalog) {
        this.inventory = inventory;
        this.roomCatalog = roomCatalog;
    }

    public List<Room> performSearch() {
        return roomCatalog.values().stream()
                .filter(room -> inventory.getCount(room.getId()) > 0)
                .collect(Collectors.toList());
    }
}

// --- 4. MAIN ENTRY POINT (Execution) ---
public class BookMyStayApp {
    public static void main(String[] args) {
        Map<String, Room> catalog = new HashMap<>();
        catalog.put("101", new Room("101", "Deluxe Suite", 250.0, List.of("WiFi", "Ocean View", "Mini Bar")));
        catalog.put("102", new Room("102", "Standard Twin", 120.0, List.of("WiFi", "TV")));
        catalog.put("103", new Room("103", "Single Budget", 75.0, List.of("WiFi")));

        Inventory hotelInventory = new Inventory();
        hotelInventory.setAvailability("101", 3);
        hotelInventory.setAvailability("102", 0);
        hotelInventory.setAvailability("103", 5);

        SearchService searchService = new SearchService(hotelInventory, catalog);

        System.out.println("--- Guest Room Search Results ---");
        List<Room> availableRooms = searchService.performSearch();

        if (availableRooms.isEmpty()) {
            System.out.println("No rooms currently available.");
        } else {
            availableRooms.forEach(System.out::println);
        }

        System.out.println("\n(System State: Unchanged)");
    }
}