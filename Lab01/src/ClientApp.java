import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientApp {

    private boolean isRunning;

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

            Thread inbound = new Thread () {
                public void run () {
                    while (true){
                        try {
                            System.out.println(socketInput.readLine());
                        } catch (IOException ioe) {
                            System.out.println("Exception occurred while receiving an inbound message:\n" + ioe);
                        }
                    }
                }
            };
            inbound.setDaemon(true);
            inbound.start();

            while (true) {
                String input = commandLineReader.nextLine();
                if (input.equals("exit")) System.exit(0);

                socketOutput.println(input);
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}