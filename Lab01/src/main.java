import java.io.IOException;
import java.net.ServerSocket;
import java.util.Scanner;

public class main {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        while (true){
            System.out.println("Server or client? s/c");
            String resp = s.nextLine();

            if (resp.equals("s")){
                try {
                    new ServerController(1234);
                } catch (Exception e) {
                    System.out.println("Failed to create server!\n" + e);
                    continue;
                }

                /*
                try(
                        ServerSocket serverSocket = new ServerSocket(1234)){
                        System.out.println("starting server...");
                        new ServerApp(serverSocket.accept()).start();
                        s.nextLine();
                        break;

                }catch (Exception e){
                    e.printStackTrace();
                }
                */
            }

            if (resp.equals("c")){
                System.out.println("Starting client...");
                new ClientApp();
                break;
            }

            if (resp.equals("x")) break;

            System.out.println("Invalid, try again");
        }
    }
}
