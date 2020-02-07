import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientApp {

    public ClientApp () {
        int portNumber = 1234;
        try {
            //Spin up socket for connection, PrintWriter for sending data through socket
            Socket clientSocket = new Socket("localhost", portNumber);
            PrintWriter socketOutput = new PrintWriter(clientSocket.getOutputStream(), true);

            Scanner commandLineReader = new Scanner(System.in);
            String userInput;
            while (true) {
                userInput = commandLineReader.nextLine();
                socketOutput.println(userInput);
                System.out.println("server sent back: " + userInput);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}