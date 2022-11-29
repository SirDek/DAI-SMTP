package configuration;

import java.io.*;
import java.util.LinkedList;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe DataReader
 * Cette classe a pour but de lire les informations concernant le contenu et les destinataires des mails
 * @author Laetitia Guidetti
 * @author Cédric Centeno
 * Date : 25.11.2022
 */
public class DataReader {

    final static private String ADDRESS_PATH = "address.txt";
    final static private String FAKE_MAIL_PATH = "fakeEmail.txt";
    final static private String SEPARATOR = "#";
    final static private String END_LINE = "\r\n";
    final static Logger LOG = Logger.getLogger(DataReader.class.getName());
    static private final ClassLoader CLASS_LOADER = DataReader.class.getClassLoader();

    /**
     * Récupère toutes les adresses mails stockés dans les resources (une par ligne)
     * @return             La LinkedList contenant toutes les adresses mails lues (non vérifiées, elles peuvent être non conforme)
     * @throws IOException Si problème lors de la lecture du fichier
     */
    public static LinkedList<String> readMailAdresse() throws IOException {

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(Objects.requireNonNull(CLASS_LOADER.getResource(ADDRESS_PATH)).getFile()));

            LinkedList<String> mailAdresses = new LinkedList<>();
            String line;

            // Lis tant qu'il y a des lignes à lire
            while((line = reader.readLine()) != null) {
                mailAdresses.add(line);
            }

            reader.close();

            return mailAdresses;
        } catch (IOException ex) {
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
     * @return             La LinkedList contenant toutes les adresses mails lues (non vérifiées, elles peuvent être non conforme)
     * @throws IOException Si problème lors de la lecture du fichier
     */
    public static LinkedList<String> readFakeMail() throws IOException {

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(Objects.requireNonNull(CLASS_LOADER.getResource(FAKE_MAIL_PATH)).getFile()));

            LinkedList<String> fakeMails = new LinkedList<>();
            String line;
            StringBuilder fakeMail = new StringBuilder();

            // Lit tant qu'il y a des lignes à lire
            while ((line = reader.readLine()) != null) {
                // Un mail est complet si le séparateur est trouvé
                if (line.equals(SEPARATOR)) {
                    fakeMails.add(fakeMail.toString());
                    fakeMail = new StringBuilder();
                } else {
                    fakeMail.append(line).append(END_LINE);
                }

            }
            // Le dernier mail n'a pas de séparateur
            fakeMails.add(fakeMail.toString());

            reader.close();
            return fakeMails;

        } catch (IOException ex) {
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
