package donnee;

/**
 * Classe ServerInfo
 * Cette classe a pour but de stocker les informations permettant de se connecter à un serveur
 * @author Laetitia Guidetti
 * @author Cédric Centeno
 * Date : 25.11.2022
 */
public class ServerInfo {

    final String HOST;
    final int PORT;

    /**
     * Constructeur de ServerInfo
     * @param host      L'adresse IP du serveur à atteindre
     * @param port      Le port sur le quel écoute le serveur
     */
    public ServerInfo(String host, int port) {
        HOST = host;
        PORT = port;
    }

    /**
     * Permet d'obtenir l'hôte à atteindre
     * @return   L'adresse IP
     */
    public String getHost() {
        return HOST;
    }


    /**
     * Permet d'obtenir le port à atteindre
     * @return  Le port
     */
    public int getPort() {
        return PORT;
    }
}
