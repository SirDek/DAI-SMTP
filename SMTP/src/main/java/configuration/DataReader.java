package configuration;

import java.io.*;
import java.util.LinkedList;
import java.util.Objects;

/**
 * Classe DataReader
 * Cette classe a pour but de lire les informations concernant le contenu et les destinataires des mails
 * @author Laetitia Guidetti
 * @author Cédric Centeno
 * Date : 25.11.2022
 */
public class DataReader {

    final static private String ADDRESS_PATH = "adresse.txt";
    final static private String FAKE_MAIL_PATH = "fakeMail.txt";
    final static private String SEPARATOR = "#";
    final static private String END_LINE = "\r\n";
    static ClassLoader classLoader = DataReader.class.getClassLoader();

    /**
     * Récupère toutes les adresses mails stockés dans les resources (une par ligne)
     * @return             La LinkedList contenant toutes les adresses mails lues (non vérifiées, elles peuvent être non conforme)
     * @throws IOException Si problème lors de la lecture du fichier
     */
    public static LinkedList<String> readMailAdresse() throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader(Objects.requireNonNull(classLoader.getResource(ADDRESS_PATH)).getFile()));

        LinkedList<String> mailAdresses = new LinkedList<>();
        String line;

        // Lis tant qu'il y a des lignes à lire
        while((line = reader.readLine()) != null) {
            mailAdresses.add(line);
        }

        reader.close();

        return mailAdresses;
    }

    /**
     * Récupère tous les contenus des mails stockés dans les resources (séparé par un #)
     * @return             La LinkedList contenant toutes les adresses mails lues (non vérifiées, elles peuvent être non conforme)
     * @throws IOException Si problème lors de la lecture du fichier
     */
    public static LinkedList<String> readFakeMail() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(Objects.requireNonNull(classLoader.getResource(FAKE_MAIL_PATH)).getFile()));

        LinkedList<String> fakeMails = new LinkedList<>();
        String line;
        StringBuilder fakeMail = new StringBuilder();

        // Lit tant qu'il y a des lignes à lire
        while((line = reader.readLine()) != null) {

            // Un mail est complet si le séparateur est trouvé
            if(line.equals(SEPARATOR)) {
                fakeMails.add(fakeMail.toString());
                fakeMail = new StringBuilder();
            }
            else {
                fakeMail.append(line).append(END_LINE);
            }
        }
        // Le dernier mail n'a pas de séparateur
        fakeMails.add(fakeMail.toString());

        reader.close();
        return fakeMails;
    }
}
