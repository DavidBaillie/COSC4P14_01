import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

/**
 * Wrapper class used by the ServerController to act as a capsule for a single Socket connection.
 * Class spins up a thread to continually read in data from client and logs it to the master class.
 * Additionally allows others to send a message through this class and handle closing Socket.
 */
public class ServerConnection {

    private Socket localSocket;
    private ServerController parent;

    private BufferedReader socketInboundReader;
    private PrintWriter socketOutboundWriter;

    public String localRoomName = GeneralData.DEFAULT_ROOM_NAME;
    public String localUsername = "";

    /**
     * Constructor
     * @param socket Socket this instance is responsible for
     * @param parent Parent ServerController this class is slave to
     */
    public ServerConnection (Socket socket, ServerController parent) {
        //Save inbound data
        this.localSocket = socket;
        this.parent = parent;

        //Log new connection
        System.out.println("Generated new connection: " + this.toString());

        //Set up the inbound and outbound reader/writer
        try {
            socketInboundReader = new BufferedReader
                    (new InputStreamReader(this.localSocket.getInputStream()));
            socketOutboundWriter = new PrintWriter(this.localSocket.getOutputStream(), true);
        } catch (IOException ioe) {
            System.out.println("Server connection instance failed to start socket reader/write:\n" + ioe);
        }

        //Anonymous thread looking for inbound socket data
        Thread inboundWatcher = new Thread() {
            public void run () {
                //Continually check for message
                while (true) {
                    try {
                        //Grab message and check for bad data
                        String incoming = socketInboundReader.readLine();
                        if (incoming == null) {
                            closeConnection();
                            break;
                        }

                        //Send the message off to the parser for the correct action
                        parseMessage(incoming);

                    //Close the connection if we hit a socket exception
                    }catch (SocketException se) {
                        System.out.println("SocketException Occurred: " + ServerConnection.this.toString());
                        closeConnection();
                        break;

                    //Log any IO exceptions
                    } catch (IOException ioe) {
                        System.out.println("Exception occurred during inbound reading:\n" + ioe);
                    }
                }
            }
        };
        inboundWatcher.setDaemon(true); //Close thread when main dies
        inboundWatcher.start();         //Start thread
    }

    /**
     * Called to have the local socket send a message to the associated client
     * @param message Message to send
     */
    public void sendMessage (String message) {
        socketOutboundWriter.println(message);
    }

    /**
     * Closes this socket connection
     */
    private void closeConnection () {
        //Remove this socket from the master list
        parent.connections.remove(this);

        //Try to formally close the socket
        try {
            localSocket.close();
        } catch (IOException ioe) {
            System.out.println("An IOException occurred while trying to close the socket: " +
                    ioe.toString() + "\n" + ioe);
        }
    }

    /**
     * Takes the inbound client message and determines the correct action to take
     * @param message Inbound message
     */
    private void parseMessage (String message) {
        String[] parsedMessage = message.split(GeneralData.DELIMITER);

        //System.out.println("Received message: " + message + "\n1) " + parsedMessage[0] + "\n2) " + parsedMessage[1] + "\n3) " + parsedMessage[3]);
        System.out.println("Received message: " + message);

        //If the message is in the wrong format, do nothing
        if (parsedMessage.length < 3) {
            System.out.println("WARNING - Server received a message in the wrong format:\n" + message);
            return;
        }

        //Extract the info from the message
        String roomName = parsedMessage[0];
        String username = parsedMessage[1];

        //Rebuilt anything lost from the message if the user used our command in the string
        String pMessage = "";
        for (int i = 2; i < parsedMessage.length; i++){
            //If we're not at the last segment, add the delimiter back in
            pMessage += i == parsedMessage.length - 1 ?
                    parsedMessage[i] : (parsedMessage[i] + GeneralData.DELIMITER);
        }

        //Load the rebuilt data into a new array
        parsedMessage = new String[] {roomName, username, pMessage};


        //Initialization command
        if (pMessage.startsWith(GeneralData.INITIALIZE_COMMAND)){
            System.out.println("Initializing connection: " + this.toString());
            localUsername = username;
            localRoomName = roomName;
            return;
        }

        //Updates the users room name
        if (pMessage.startsWith(GeneralData.CHANGE_ROOM_COMMAND)){
            String newRoom = parsedMessage[2].substring(2);
            newRoom.trim();
            System.out.println("Changing " + localUsername + "'s room to " + newRoom);
            localRoomName = newRoom;
            socketOutboundWriter.println("/cr" + newRoom);
            return;
        }

        //Closes the connection as requested by client
        if (pMessage.startsWith(GeneralData.CLOSE_COMMAND)){
            System.out.println("Called to close connection: " + this.toString());
            closeConnection();
            return;
        }

        //Default message, just log it
        parent.queuedMessages.add(parsedMessage);
    }
}
