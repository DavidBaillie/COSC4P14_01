import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

  public static void main(String[] args) throws Exception {

    try (Socket socket = new Socket("localhost", 1234)) {
      menuItems();
      Scanner scanner = new Scanner(System.in);
      Scanner input = new Scanner(socket.getInputStream());
      PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
      //BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      System.out.println("enter username");
        while (scanner.hasNextLine()) {
             out.println(scanner.nextLine());
        }



    }
  }

  static void menuItems() {
    System.out.println("************* MENU ITEMS ***********************");
    System.out.println("Ctrl+C to quit");
    System.out.println("enter '*FILE' to transfer a file");
    System.out.println("************************************************");
  }


}