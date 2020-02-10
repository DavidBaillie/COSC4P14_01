import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ServerController {

    public List<ServerConnection> connections;
    public ConcurrentLinkedQueue<String[]> queuedMessages;

    private ServerSocket serverSocket;

    /**
     * Constructor
     * @param port Port to open on server
     */
    public ServerController(int port) {
        connections = new ArrayList<>();
        queuedMessages = new ConcurrentLinkedQueue<>();

        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException ioe) {
            System.out.println("Server failed to open socket on part: \n" + ioe);
            return;
        }

        //Create anonymous class on Thread that tries to accept any new connection
        Thread inboundConnections = new Thread() {
            //Threaded method
            public void run() {
                //Keep trying to accept connections forever
                while (true) {
                    try {
                        //Open socket and add connection to List
                        Socket newConnection = serverSocket.accept();
                        connections.add(new ServerConnection(newConnection, ServerController.this));
                    } catch (IOException ioe) {
                        System.out.println("An IOException occurred while trying to accept an inbound " +
                                "connection on the server:\n" + ioe);
                    }
                }
            }
        };
        inboundConnections.setDaemon(true); //Kill thread when main process ends
        inboundConnections.start();         //Start thread

        //Anonymous thread used to continually read from the message queue and send to clients
        Thread queuedMessageReader = new Thread() {
            public void run() {
                while (true) {
                    //If queue is empty wait 100ms then try again
                    if (queuedMessages.isEmpty()) {
                        try {
                            sleep(100);
                        } catch (InterruptedException ie) {
                            System.out.println("Failed sleep:\n" + ie);
                        }
                        continue;
                    }

                    //Otherwise queue has stuff to read
                    handleMessage(queuedMessages.poll());
                }

            }
        };
        queuedMessageReader.setDaemon(true);    //Kill thread when main dies
        queuedMessageReader.start();            //Start thread
    }

    /**
     * Takes a queued message from the pool of inbound messages and responds to it
     * @param message Message to handle
     */
    private synchronized void handleMessage (String[] message) {
        for (ServerConnection sc : connections) {
            if (sc.localRoomName.equals(message[0])){
                System.out.println("Sending message to " + message[1] + "\n" +
                        sc.localRoomName + "/" + message[0]);
                sc.sendMessage(message[1] + ": " + message[2]);
            }
        }
    }


    public void sendDisconnectMessage (String name) {
        for (ServerConnection conn : connections)
            conn.sendMessage(name + " has left");
    }
}
