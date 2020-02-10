import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

  public static void main(String[] args) throws Exception {
    try (ServerSocket listener = new ServerSocket(1234)) {
      System.out.println("server is running...");
      ExecutorService pool = Executors.newFixedThreadPool(10);
      while (true) {
        pool.execute(new Handler(listener.accept()));
      }
    }
  }

  private static class Handler implements Runnable {
    private Socket socket;
    private LinkedList<User> users;

    Handler(Socket socket) {
      this.socket = socket;
      users = new LinkedList<User>();
    }

    private boolean userExists() {
      int port = socket.getPort();
      boolean exists = false;
      for (int i = 0; i < users.size(); i++) {
        if (users.get(i).getPort() == port) {
          exists = true;
        }
      }
      return exists;
    }

    private String getUser(int portNumber) {
      String s = "NULL USER";
      for (int i = 0; i < users.size(); i++) {
        if (users.get(i).getPort() == portNumber) {
          s = users.get(i).getName();
        }
      }
      return s;
    }

    private void removeUser(int portNum) {
      for (int i = 0; i < users.size(); i++) {
        if (users.get(i).getPort() == portNum) {
          users.remove(users.get(i));
        }
      }
    }

    @Override
    public void run() {
      System.out.println("Connected: " + socket);
      try {
        Scanner in = new Scanner(socket.getInputStream());
        PrintWriter outWriter = new PrintWriter(socket.getOutputStream(), true);
        while (in.hasNextLine()) {
          String line;
          if (!userExists()) {
            line = in.nextLine();
            System.out.println(line + " has joined chat");//prints user added to chat
            User user = new User(line, socket.getPort());
            users.add(user);
          } else {
            int incomingPort = socket.getPort();
            String username = getUser(incomingPort);
            line = in.nextLine();
            String broadcast = username + " says: " + line;
            System.out.println(broadcast);
            outWriter.println(broadcast);
          }
        }


      } catch (Exception e) {
        System.out.println(e);
      } finally {
        try {
          socket.close();
        } catch (IOException e) {
        }
        String userLeftChat = getUser(socket.getPort());
        removeUser(socket.getPort());
        String broadcast = userLeftChat + " left chatroom";
        System.out.println(broadcast);
      }
    }


  }
}