import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerApp  extends Thread{

    private Socket serverSocket;

    /**
     * Constructor
     * @param socket Socket for thread to use on connection
     */
    public ServerApp(Socket socket) {
        this.serverSocket = socket;
    }

    /**
     * Threaded run method
     */
    @Override
    public void run() {
        try {
            BufferedReader socketInboundReader = new BufferedReader(new InputStreamReader(this.serverSocket.getInputStream()));
            PrintWriter socketOutboundWriter = new PrintWriter(this.serverSocket.getOutputStream(), true);

            while (true){
                String inboundData = socketInboundReader.readLine();
                //socketOutboundWriter.println("Echo:" + inboundData);
                socketOutboundWriter.println("Hello there youngling...");
                System.out.println("user: saw " + inboundData);
                if (inboundData.equals("done")){
                    break;
                }

                try {
                    Thread.sleep(1500);
                }catch (InterruptedException e){
                    System.out.println("Thread was interrupted ");
                }

            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}