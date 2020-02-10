import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientApp {

    private PrintWriter socketOutput;
    private BufferedReader socketInput;

    public ClientApp () {
        int portNumber = 1234;
        try {
            String ip;
            Socket clientSocket;

            //Grab a valid server IP/Host to connect to
            while (true){
                try {
                    System.out.println("Please enter your ip (or localhost)");
                    ip = new Scanner(System.in).nextLine();
                    clientSocket = new Socket(ip, portNumber);
                    break;
                } catch (SocketException se) {
                    System.out.println("Invalid IP, please try again");
                } catch (UnknownHostException uhe) {
                    System.out.println("Invalid IP, please try again");
                } catch (Exception e) {
                    System.out.println("Invalid IP, please try again");
                }
            }

            //Connection made, log and continue
            System.out.println("Connection established...");
            //Used to send strings to the server
            socketOutput = new PrintWriter(clientSocket.getOutputStream(), true);
            //Used to receive strings from the server
            socketInput = new BufferedReader
                    (new InputStreamReader(clientSocket.getInputStream()));

            //Spin up Scanner for input
            Scanner commandLineReader = new Scanner(System.in);

            //Create an anonymous thread to wait for inbound data on the socket
            Thread inbound = new Thread () {
                public void run () {
                    while (true){
                        try {
                            //Pass message to method for handling
                            clientReceivedMessage(socketInput.readLine());
                        } catch (IOException ioe) {
                            System.out.println("Exception occurred while receiving an inbound message:\n" + ioe);
                        }
                    }
                }
            };
            inbound.setDaemon(true);    //Kills the thread when main quits
            inbound.start();            //Starts the thread

            UserData.roomName = GeneralData.DEFAULT_ROOM_NAME;
            //Send the server our initial data
            socketOutput.println(UserData.roomName + GeneralData.DELIMITER + UserData.username +
                            GeneralData.DELIMITER + GeneralData.INITIALIZE_COMMAND);

            //Allow the user to repeatedly input information
            while (true) {
                clientEnteredMessage(commandLineReader.nextLine());
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Called by the receiver thread when the socket gets a message from the server
     * @param message Message received
     */
    private void clientReceivedMessage (String message) {
        //
        if (message == null) {
            System.out.println("Closing connection...");
            System.exit(0);
        }

        //Received command to change room name
        if (message.startsWith("/cr")){
            UserData.roomName = message.substring(3);
            return;
        }

        //Otherwise print the new message
        System.out.println(message);
    }

    /**
     * Called when the user enters a message to the console, determines what action to take
     * @param message Message user entered
     */
    private void clientEnteredMessage (String message) {
        if (message.equals("exit")) quitApplication();

        //Build message and send to server
        socketOutput.println(UserData.roomName + GeneralData.DELIMITER + UserData.username +
                GeneralData.DELIMITER + message);
    }

    /**
     * Handles closing the application
     */
    private static void quitApplication () {
        System.exit(0);
    }
}