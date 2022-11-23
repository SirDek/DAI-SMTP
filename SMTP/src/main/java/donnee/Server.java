package donnee;

public class Server {

    final String HOST;
    final int PORT;

    public Server(String host, int port) {
        HOST = host;
        PORT = port;
    }

    public String getHost() {
        return HOST;
    }

    public int getPort() {
        return PORT;
    }
}
