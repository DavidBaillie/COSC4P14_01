import java.util.Scanner;

/**
 * Main class handles taking user choices and starts up the correct code
 */
public class main {

    /**
     * Main program call
     * @param args Command line args
     */
    public static void main(String[] args) {
        //Grab Scanner for command line reading
        Scanner s = new Scanner(System.in);
        
        //Loop forever until the user chooses the correct info
        while (true){
            System.out.println("Server or client? s/c");
            String resp = s.nextLine();

            //User has asked to start a server
            if (resp.equals("s")){
                try {
                    System.out.println("Starting server");
                    new ServerController(1234);
                    break;
                } catch (Exception e) {
                    System.out.println("Failed to create server!\n" + e);
                    continue;
                }
            }

            //User asked to be a client
            if (resp.equals("c")){
                try {
                    UserData.requestUsername();
                    System.out.println("Starting client...");
                    new ClientApp();
                    UserData.roomName = GeneralData.DEFAULT_ROOM_NAME;
                    break;
                } catch (Exception ioe) {
                    System.out.println("Failed to create client session");
                    continue;
                }

            }

            //User asked to quit
            if (resp.equals("x")) break;

            System.out.println("Invalid, try again");
        }

        //Suspend main process while application runs until we get an exit command
        while (true){
            System.out.println("type 'exit' at any time to quit");
            if (s.nextLine().equals("exit")) break;
        }
    }
}
