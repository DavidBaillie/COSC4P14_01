import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientApp {

    public ClientApp () {
        int portNumber = 1234;
        try {
            Socket clientSocket = new Socket("localhost", portNumber);
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

            String input;

//            while ((input = stdIn.readLine()) != null){
            //              out.println(input);
            //            System.out.println(input);
            //     }


            Scanner scan = new Scanner(System.in);
            String s;
            while (true) {

                s = scan.nextLine();
                out.println(s);
                //out.flush();
                System.out.println("server sent back: " + s);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}