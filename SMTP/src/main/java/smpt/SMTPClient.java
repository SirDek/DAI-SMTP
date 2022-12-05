package smpt;
import data.*;
import java.io.*;
import java.net.Socket;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * La classe SMTPClient
 * Cette classe a pour rôle d'envoyer des emails à un serveur SMTP
 * @author Laetitia Guidetti
 * @author Cédric Centeno
 * Date : 25.11.2022
 */
public class SMTPClient {
    final static Logger LOG = Logger.getLogger(SMTPClient.class.getName());
    Email email;
    ServerInfo server;

    /**
     * Le constructeur de la classe SMTP client
     * @param email     Permet l'obtention des adresses emails, entêtes, et corp de l'email.
     * @param server    Permet l'obtention de l'adresse IP et du numéro de port du serveur SMTP
     *                  auquel on souhaite se connecter.
     */
    public SMTPClient(Email email, ServerInfo server) {
        this.email = email;
        this.server = server;
    }

    /**
     * Envoie des emails prank à travers le protocole SMTP
     */
    public void send() throws IOException {

        // Réception des informations du serveur SMTP avec lequel on souhaite communiquer.
        final String SMTP_HOST = server.getHost();
        final int port = server.getPort();

        final String END_LINE = "\r\n";
        final String FORMAT = "UTF-8";
        final String ERROR_MSG = "ERROR with message recieved : ";

        Socket socket = null;
        BufferedReader in = null;
        BufferedWriter out = null;

        try {
            // Connection au serveur à travers une socket.
            socket = new Socket(SMTP_HOST, port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream(), FORMAT));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), FORMAT));

            // Lecture du message d'accueil du serveur.
            String line = in.readLine();

            // Identification du client
            out.write("EHLO prankster" + END_LINE);
            out.flush();

            // Réception de la réponse du serveur, contrôle du code de status.
            line = in.readLine();
            if (!line.startsWith("250")) {
                out.write("QUIT" + END_LINE);
                out.flush();
                // Réponse du serveur
                line = in.readLine();
                throw new IOException(ERROR_MSG + line);
            }
            // Récupération des informations sur les capacités du serveur.
            while (line.startsWith("250-")) {
                line = in.readLine();
            }

            // Envoi de l'adresse email de l'envoyeur.
            out.write("MAIL FROM:" + email.getSender() + END_LINE);
            out.flush();

            // Réception de la réponse du serveur, contrôle du code de status.
            line = in.readLine();
            if (!line.startsWith("250")) {
                out.write("QUIT" + END_LINE);
                out.flush();
                // Réponse du serveur
                line = in.readLine();
                throw new IOException(ERROR_MSG + line);
            }

            // Envoi des adresses email des destinataires.
            for (String reciever : email.getReceivers()) {
                out.write("RCPT TO:" + reciever + END_LINE);// ;
                out.flush();

                // Réception de la réponse du serveur, contrôle du code de status.
                line = in.readLine();
                if (!line.startsWith("250")) {
                    out.write("QUIT" + END_LINE);
                    out.flush();
                    // Réponse du serveur
                    line = in.readLine();
                    throw new IOException(ERROR_MSG + line);
                }
            }

            // Initialisation de la transmission du courier.
            out.write("DATA" + END_LINE);
            out.flush();
            line = in.readLine();

            // Réception de la réponse du serveur, contrôle du code de status.
            if (!line.startsWith("354")) { 
                out.write("QUIT" + END_LINE);
                out.flush();
                // Réponse du serveur
                line = in.readLine();
                throw new IOException(ERROR_MSG + line);
            }
            // Entêtes email.
            out.write("From: " + email.getSender() + END_LINE);
            out.write("To: ");
            LinkedList<String> reciever = email.getReceivers();
            out.write(reciever.get(0));
            for (int i = 1; i < email.getReceivers().size(); i++) {
                out.write(", " + reciever.get(i));
            }
            out.write(END_LINE);

            // Spécification du format du plain text.
            out.write("Content-Type: text/plain; charset=utf-8" + END_LINE);

            // Envoi du texte contenant l'entête sujet ainsi que le contenu de la prank.
            out.write(email.getFakeEmail());
            out.write('.' +END_LINE);
            out.flush();

            // Réception de la réponse du serveur, contrôle du code de status.
            line = in.readLine();
            if (!line.startsWith("250")) {
                out.write("QUIT" + END_LINE);
                out.flush();
                // Réponse du serveur
                line = in.readLine();
                throw new IOException(ERROR_MSG + line);
            }
            // Fin de la communication.

            out.write("QUIT" + END_LINE);
            out.flush();
            // Réponse du serveur
            line = in.readLine();
            // Fermeture des buffers ainsi que de la socket.
            in.close();
            out.close();
            socket.close();

            // Catch les exceptions, ferme les buffers/socket s'ils sont encore ouverts.
        } catch (IOException ex) {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ex1) {
                    LOG.log(Level.SEVERE, ex1.getMessage(), ex1);
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ex1) {
                    LOG.log(Level.SEVERE, ex1.getMessage(), ex1);
                }
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException ex1) {
                    LOG.log(Level.SEVERE, ex1.getMessage(), ex1);
                }
            }
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
            throw ex;
        }
    }
}
