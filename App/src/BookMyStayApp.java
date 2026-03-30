import java.util.*;
import java.time.LocalDateTime;

// --- 1. THE DATA MODEL (Guest Intent) ---
class ReservationRequest {
    private final String requestId;
    private final String guestName;
    private final String roomTypeId;
    private final LocalDateTime timestamp;

    public ReservationRequest(String guestName, String roomTypeId) {
        this.requestId = UUID.randomUUID().toString().substring(0, 8);
        this.guestName = guestName;
        this.roomTypeId = roomTypeId;
        this.timestamp = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return String.format("[%s] Guest: %-10s | Room: %-12s | Time: %s",
                requestId, guestName, roomTypeId, timestamp.toLocalTime());
    }
}

// --- 2. THE QUEUE MANAGER (Intake Mechanism) ---
class BookingQueueManager {
    // Using a Queue to preserve arrival order (FIFO)
    private final Queue<ReservationRequest> requestQueue = new LinkedList<>();

    // Intake: No inventory is changed here!
    public void submitRequest(ReservationRequest request) {
        requestQueue.add(request);
        System.out.println(">>> Request Queued: " + request.toString());
    }

    public boolean hasRequests() {
        return !requestQueue.isEmpty();
    }

    // This would be called by the Allocation System later
    public ReservationRequest nextRequest() {
        return requestQueue.poll();
    }

    public int getQueueSize() {
        return requestQueue.size();
    }
}

// --- 3. MAIN EXECUTION ---
public class BookMyStayApp {
    public static void main(String[] args) throws InterruptedException {
        BookingQueueManager queueManager = new BookingQueueManager();

        System.out.println("--- System: Booking Intake Started (FIFO) ---");

        // Simulating rapid-fire requests (Arrival Order)
        queueManager.submitRequest(new ReservationRequest("Alice", "Deluxe Suite"));
        Thread.sleep(100); // Simulate tiny delay in network arrival
        queueManager.submitRequest(new ReservationRequest("Bob", "Standard Room"));
        Thread.sleep(100);
        queueManager.submitRequest(new ReservationRequest("Charlie", "Deluxe Suite"));

        System.out.println("\n--- Current Queue Status ---");
        System.out.println("Total requests waiting: " + queueManager.getQueueSize());

        System.out.println("\n--- Preparation for Allocation (Processing Order) ---");
        while (queueManager.hasRequests()) {
            ReservationRequest processing = queueManager.nextRequest();
            System.out.println("Ready to process: " + processing);
            // Allocation logic (Use Case 6) would happen here.
        }
    }
}