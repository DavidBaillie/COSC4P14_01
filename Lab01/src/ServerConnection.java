import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerConnection {

    private Socket localSocket;
    private ServerController parent;

    private BufferedReader socketInboundReader;
    private PrintWriter socketOutboundWriter;

    public ServerConnection (Socket socket, ServerController parent) {
        this.localSocket = socket;
        this.parent = parent;

        System.out.println("Generated new connection: " + this.toString());

        try {
            socketInboundReader = new BufferedReader
                    (new InputStreamReader(this.localSocket.getInputStream()));
            socketOutboundWriter = new PrintWriter(this.localSocket.getOutputStream(), true);
        } catch (IOException ioe) {
            System.out.println("Server connection instance failed to start socket reader/write:\n" + ioe);
        }


        Thread inboundWatcher = new Thread() {
            public void run () {
                while (true) {
                    try {
                        String incoming = socketInboundReader.readLine();
                        parent.queuedMessages.add(incoming);
                        System.out.println("Connection added new message: " + incoming);
                    } catch (IOException ioe) {
                        System.out.println("Exception occurred during inbound reading:\n" + ioe);
                    }
                }
            }
        };
        inboundWatcher.setDaemon(true);
        inboundWatcher.start();
    }


    public void sendMessage (String message) {
        System.out.println("Received send request :: " + message + " :: " + this.toString());
        socketOutboundWriter.println(message);
    }
}
