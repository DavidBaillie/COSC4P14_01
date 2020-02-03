
import java.util.Scanner;

public class main {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        while (true){
            System.out.println("Server or client? s/c");
            String resp = s.nextLine();

            if (resp.equals("s")){
                System.out.println("starting server...");
                new ServerApp();
                break;
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
