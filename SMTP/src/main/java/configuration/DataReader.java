package configuration;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe DataReader
 * Cette classe a pour but de lire les informations concernant le contenu et les destinataires des mails
 * @author Laetitia Guidetti
 * @author Cédric Centeno
 * Date : 06.12.2022
 */
public class DataReader {
    final static private Logger LOG = Logger.getLogger(DataReader.class.getName());

    /**
     * Récupère toutes les adresses mails stockés dans les resources (une par ligne)
     * @return             La LinkedList contenant toutes les adresses emails lues (non vérifiées, elles peuvent être non conforme)
     * @throws IOException Si problème lors de la lecture du fichier
     */
    public static LinkedList<String> readMailAdresse() throws IOException {

        final String ADDRESS_PATH = "address.txt";

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(DataReader.class.getClassLoader().getResourceAsStream(ADDRESS_PATH)),
                                                              StandardCharsets.UTF_8));

            LinkedList<String> mailAdresses = new LinkedList<>();
            String line;

            // Lis tant qu'il y a des lignes à lire
            while((line = reader.readLine()) != null) {
                mailAdresses.add(line);
            }

            reader.close();
            return mailAdresses;

        } catch (IOException ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ex1) {
                    LOG.log(Level.SEVERE, ex1.getMessage(), ex1);
                }
            }
            throw ex;
        }
    }

    /**
     * Récupère tous les contenus des mails stockés dans les resources (séparé par un #)
     * @return             La LinkedList contenant tous les emails lus
     * @throws IOException Si problème lors de la lecture du fichier
     */
    public static LinkedList<String> readFakeMail() throws IOException {

        final String FAKE_MAIL_PATH = "fakeEmail.txt";
        final String SEPARATOR = "#";
        final String END_LINE = "\r\n";

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(DataReader.class.getClassLoader().getResourceAsStream(FAKE_MAIL_PATH)),
                                                              StandardCharsets.UTF_8));

            LinkedList<String> fakeMails = new LinkedList<>();
            String line;
            StringBuilder fakeMail = new StringBuilder();

            // Lit tant qu'il y a des lignes à lire
            while ((line = reader.readLine()) != null) {
                // Un email est complet si le séparateur est trouvé
                if (line.equals(SEPARATOR)) {
                    fakeMails.add(fakeMail.toString());
                    fakeMail = new StringBuilder();
                } else {
                    fakeMail.append(line).append(END_LINE);
                }

            }
            // Le dernier email n'a pas de séparateur
            fakeMails.add(fakeMail.toString());

            reader.close();
            return fakeMails;

        } catch (IOException ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ex1) {
                    LOG.log(Level.SEVERE, ex1.getMessage(), ex1);
                }
            }
            throw ex;
        }
    }
}
