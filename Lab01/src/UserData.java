import java.util.Scanner;

/**
 * Static class used to hold onto user information about the current session
 */
public class UserData {
    public static String username = "";
    public static String roomName = "";

    /**
     * Creates a Scanner wait event that requires the username to be changed
     */
    public static void requestUsername () {
        System.out.println("\nPlease enter your username:");
        username = new Scanner(System.in).nextLine();
    }
}
