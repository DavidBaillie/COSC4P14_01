import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientApp {

    public ClientApp () {
        int portNumber = 1234;
        try {
            //Spin up socket for connection
            Socket clientSocket = new Socket("localhost", portNumber);
            //Used to send strings to the server
            PrintWriter socketOutput = new PrintWriter(clientSocket.getOutputStream(), true);
            //Used to receive strings from the server
            BufferedReader socketInput = new BufferedReader
                    (new InputStreamReader(clientSocket.getInputStream()));

            //Spin up Scanner for input
            Scanner commandLineReader = new Scanner(System.in);
            String userInput;

            //Continually act as a chat program
            while (true) {
                userInput = commandLineReader.nextLine();
                socketOutput.println(userInput);
                System.out.println("server sent back: " + socketInput.readLine());
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}