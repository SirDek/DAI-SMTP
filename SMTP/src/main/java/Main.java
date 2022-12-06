import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

import configuration.Config;
import configuration.DataReader;
import data.*;
import smpt.SMTPClient;

/**
 * Classe Main
 * Chargée du lancement du programme
 * @author Laetitia Guidetti
 * @author Cédric Centeno
 * Date : 06.12.2022
 */
public class Main {
    final static private Logger LOG = Logger.getLogger(DataReader.class.getName());

    public static void main(String[] args) {

        System.out.println("Lancement du programme envoyant des pranks par emails");
        try {
            Config config = new Config();

            ServerInfo serv = config.createServer();
            System.out.println("Le serveur à atteindre est à l'adresse : " + serv.getHost() + " et au port : " + serv.getPort());

            LinkedList<Email> allEmails = config.getAllMail();

            // Envoi de chaque email
            for (Email email : allEmails) {
                SMTPClient client = new SMTPClient(email, serv);
                client.send();
            }
            System.out.println("Tout les emails ont été envoyés, fin du programme");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Fatal error - End of program");
        }
    }
}