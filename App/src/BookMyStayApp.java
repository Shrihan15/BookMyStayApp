import java.util.*;

// Booking Request Model
class BookingRequest {
    String requestId;
    String roomType;

    public BookingRequest(String requestId, String roomType) {
        this.requestId = requestId;
        this.roomType = roomType;
    }
}

// Inventory Service
class InventoryService {
    private Map<String, Integer> inventory = new HashMap<>();

    public InventoryService() {
        inventory.put("Single", 2);
        inventory.put("Double", 2);
        inventory.put("Suite", 1);
    }

    public synchronized boolean isAvailable(String roomType) {
        return inventory.getOrDefault(roomType, 0) > 0;
    }

    public synchronized void decrement(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }

    public void displayInventory() {
        System.out.println("Current Inventory: " + inventory);
    }
}

// Booking Service
class BookingService {
    private Queue<BookingRequest> requestQueue = new LinkedList<>();
    private Map<String, Set<String>> roomTypeMap = new HashMap<>();
    private Set<String> allAllocatedRooms = new HashSet<>();

    private InventoryService inventoryService;

    public BookingService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    public void addRequest(BookingRequest request) {
        requestQueue.offer(request);
    }

    // Generate unique room ID
    private String generateRoomId(String roomType) {
        return roomType.substring(0, 2).toUpperCase() + "-" + UUID.randomUUID().toString().substring(0, 6);
    }

    // Core Allocation Logic (Atomic)
    public synchronized void processBookings() {
        while (!requestQueue.isEmpty()) {
            BookingRequest request = requestQueue.poll();

            System.out.println("\nProcessing Request: " + request.requestId);

            if (!inventoryService.isAvailable(request.roomType)) {
                System.out.println("❌ No rooms available for type: " + request.roomType);
                continue;
            }

            String roomId;
            do {
                roomId = generateRoomId(request.roomType);
            } while (allAllocatedRooms.contains(roomId)); // Ensure uniqueness

            // Assign Room
            allAllocatedRooms.add(roomId);

            roomTypeMap.putIfAbsent(request.roomType, new HashSet<>());
            roomTypeMap.get(request.roomType).add(roomId);

            // Update Inventory immediately
            inventoryService.decrement(request.roomType);

            // Confirm Reservation
            System.out.println("✅ Reservation Confirmed!");
            System.out.println("Room Type: " + request.roomType);
            System.out.println("Allocated Room ID: " + roomId);

            inventoryService.displayInventory();
        }
    }

    public void displayAllocations() {
        System.out.println("\nFinal Room Allocations:");
        for (String type : roomTypeMap.keySet()) {
            System.out.println(type + " -> " + roomTypeMap.get(type));
        }
    }
}

// Main Class
public class BookMyStayApp{
    public static void main(String[] args) {

        InventoryService inventoryService = new InventoryService();
        BookingService bookingService = new BookingService(inventoryService);

        // Sample Requests (FIFO)
        bookingService.addRequest(new BookingRequest("REQ1", "Single"));
        bookingService.addRequest(new BookingRequest("REQ2", "Double"));
        bookingService.addRequest(new BookingRequest("REQ3", "Single"));
        bookingService.addRequest(new BookingRequest("REQ4", "Suite"));
        bookingService.addRequest(new BookingRequest("REQ5", "Single")); // Should fail (limited inventory)

        // Process Bookings
        bookingService.processBookings();

        // Final Allocation Summary
        bookingService.displayAllocations();
    }
}