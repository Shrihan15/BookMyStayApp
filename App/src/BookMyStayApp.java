public class BookMyStayApp {
    // Using constants makes the app easier to update later
    private static final String APP_NAME = "Book My Stay";
    private static final String VERSION = "1.0.0";

    public static void main(String[] args) {
        displayWelcomeMessage();
    }

    private static void displayWelcomeMessage() {
        System.out.println("=======================================");
        System.out.println("Welcome to " + APP_NAME);
        System.out.println("Version: " + VERSION);
        System.out.println("System Initialized Successfully.");
        System.out.println("=======================================");
    }
}
