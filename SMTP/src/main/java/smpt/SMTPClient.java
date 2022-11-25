package smpt;
import donnee.*;
import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * La classe SMTPClient.
 * Cette classe a pour role d'envoyer les email a un serveur SMTP
 * @author Laetitia Guidetti
 * @author Cédric Centeno
 * Date : 25.11.2022
 */
public class SMTPClient {
    final static Logger LOG = Logger.getLogger(SMTPClient.class.getName());
    Mail mail;
    ServerInfo server;

    /**
     * Le constructeur de la classe SMTP client
     * @param mail      Permet l'obtention des adresses emails, entetes, et corp de l'email.
     * @param server    Permet l'obtention de l'adresse IP et du numero de port du serveur SMTP
     *                  auquel on souhaite se connecter.
     */
    public SMTPClient(Mail mail, ServerInfo server) {
        this.mail = mail;
        this.server = server;
    }

    /**
     * Envoie des emails prank a travers le protocole SMTP
     */
    public void send() {

        // Reception des informations du serveur SMTP avec lequel on souhaite communiquer.
        final String SMTP_HOST = server.getHost();
        final int port = server.getPort();

        final String END_LINE = "\r\n";
        final String FORMAT = "UTF-8";
        final String ERROR_MSG = "ERROR with message recieved : ";

        Socket socket = null;
        BufferedReader in = null;
        BufferedWriter out = null;

        try {
            // Connection au serveur a travers une socket.
            socket = new Socket(SMTP_HOST, port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream(), FORMAT));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), FORMAT));

            // Lecture du message d'accueil du serveur.
            String line = in.readLine();

            // Identification du client
            out.write("EHLO prankster" + END_LINE);
            out.flush();

            // Reception de la reponse du serveur, controle du code de status
            line = in.readLine();
            if (!line.startsWith("250")) {
                out.write("QUIT" + END_LINE);
                throw new IOException(ERROR_MSG + line);
            }
            // Recuperation des informations sur les capacites du serveur.
            while (line.startsWith("250-")) {
                line = in.readLine();
            }

            // Envoi de l'adresse email de l'emetteur.
            out.write("MAIL FROM:" + mail.getSender() + END_LINE);
            out.flush();

            // Reception de la reponse du serveur, controle du code de status
            line = in.readLine();
            if (!line.startsWith("250")) {
                out.write("QUIT" + END_LINE);
                throw new IOException(ERROR_MSG + line);
            }

            // Envoi des adresses email des recepteurs.
            for (String reciever : mail.getReceivers()) {
                out.write("RCPT TO:" + reciever + END_LINE);// ;
                out.flush();

                // Reception de la reponse du serveur, controle du code de status
                line = in.readLine();
                if (!line.startsWith("250")) {
                    out.write("QUIT" + END_LINE);
                    throw new IOException(ERROR_MSG + line);
                }
            }

            // Initialisation de la transmition du courier
            out.write("DATA" + END_LINE);
            out.flush();
            line = in.readLine();

            // Reception de la reponse du serveur, controle du code de status
            if (!line.startsWith("354")) { 
                out.write("QUIT" + END_LINE);
                throw new IOException(ERROR_MSG + line);
            }
            // Entetes email.
            out.write("From: " + mail.getSender() + END_LINE);
            out.write("To: ");
            for (String reciever : mail.getReceivers()) {
                out.write( reciever + " ");
            }
            out.write(END_LINE);
            // Specification du format du plain text
            out.write("Content-Type: text/plain; charset=utf-8" + END_LINE);

            // Envoi du texte contenant l'entete sujet ainsi que le contenu de la prank
            out.write(mail.getFakeMail());
            out.write('.' +END_LINE);
            out.flush();

            // Reception de la reponse du serveur, controle du code de status
            line = in.readLine();
            if (!line.startsWith("250")) {
                out.write("QUIT" + END_LINE);
                throw new IOException(ERROR_MSG + line);
            }
            // Fin de la communication

            // Fermeture des buffers ainsi que de la socket.
            in.close();
            out.close();
            socket.close();

            // Catch les exeptions, ferme les buffers/socket s'ils sont encore ouverts.
        } catch (IOException ex) {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ex1) {
                    LOG.log(Level.SEVERE, ex.getMessage(), ex1);
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ex1) {
                    LOG.log(Level.SEVERE, ex.getMessage(), ex1);
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
        }
    }
}
