public class User {
  String name;
  int port;
  boolean newUser;

  public User(String name, int port) {
    this.name = name;
    this.port = port;
    this.newUser=true;
  }

  public String getName() {
    return name;
  }

  public int getPort() {
    return port;
  }
}
