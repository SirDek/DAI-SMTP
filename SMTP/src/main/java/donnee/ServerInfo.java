package donnee;

public class ServerInfo {

    final String HOST;
    final int PORT;

    public ServerInfo(String host, int port) {
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
