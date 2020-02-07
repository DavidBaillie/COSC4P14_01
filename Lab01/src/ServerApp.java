import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerApp  extends Thread{

    private Socket serverSocket;


    public ServerApp(Socket socket) {
        this.serverSocket = socket;
    }

    @Override
    public void run() {
        //        while (true){
        try {
            //this.serverSocket = new ServerSocket(1234, 1, InetAddress.getLocalHost());
            //this.serverSocket = new ServerSocket(1234);

            //System.out.println(serverSocket.accept());

            //Socket client = serverSocket.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(this.serverSocket.getInputStream()));
            PrintWriter out = new PrintWriter(this.serverSocket.getOutputStream(), true);

            String input;
            Scanner scanner = new Scanner(System.in);
            while (true){

                String s = in.readLine();
                out.println("Echo:" + s);
                System.out.println("user: saw " + s);
                if (s.equals("done")){
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
//        }
    }
}