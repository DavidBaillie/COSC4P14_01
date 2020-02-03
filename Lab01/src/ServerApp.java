import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerApp {

    private ServerSocket serverSocket;

    public ServerApp() {
        while (true){
            try {
                this.serverSocket = new ServerSocket(1234, 1, InetAddress.getLocalHost());
                System.out.println(serverSocket.getInetAddress());
                Socket client = serverSocket.accept();
                PrintWriter out = new PrintWriter(client.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

                String input;

                while (true){
                    String s = in.readLine();
                    out.println("Echo: " + s);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}
