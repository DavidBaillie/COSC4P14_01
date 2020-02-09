import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

  public static void main(String[] args) throws Exception {

    try (Socket socket = new Socket("localhost", 1234)) {
      System.out.println("Ctrl+D or Ctrl+C to quit");
      menuItems();
      Scanner scanner = new Scanner(System.in);
      PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
      System.out.println("enter username");

      while (scanner.hasNextLine()) {
        out.println(scanner.nextLine());

      }
    }
  }

  static void menuItems() {
    System.out.println("************* MENU ITEMS ***********************");
    System.out.println("enter '*FILE' to transfer a file");
  }


}