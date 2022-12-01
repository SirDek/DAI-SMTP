package configuration;

import data.*;
import java.io.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Classe Config
 * Cette classe a pour but de créer les objets stockant les diverses informations en vérifiant leur coherence
 * @author Laetitia Guidetti
 * @author Cédric Centeno
 * Date : 25.11.2022
 */
public class Config {

    final static Logger LOG = Logger.getLogger(Config.class.getName());
    final static private String CONFIG_PATH = "config.properties";
    final static private Pattern ADDRESS_PATTERN = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    private final int NB_GROUP;
    final private Properties PROPERTIES;


    /**
     * Constructeur de Config
     * @throws IOException  Si impossible d'accéder aux configurations
     */
    public Config() throws IOException {
        InputStream input = null;
        PROPERTIES = new Properties();
        try {
            input = Config.class.getClassLoader().getResourceAsStream(CONFIG_PATH);
            PROPERTIES.load(input);
            NB_GROUP = Integer.parseInt(PROPERTIES.getProperty("nbGroupe"));
        }
        catch (IOException ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
            if (input != null) {
                try {
                    input.close();
                } catch (IOException ex1) {
                    LOG.log(Level.SEVERE, ex1.getMessage(), ex1);
                }
            }
            throw ex;
        }
    }

    /**
     * Permet d'obtenir un objet contenant les informations permettant de se connecter à un serveur
     * @return  Un objet de la classe ServerInfo
     */
    public ServerInfo createServer() {
        return new ServerInfo(PROPERTIES.getProperty("host"), Integer.parseInt(PROPERTIES.getProperty("port")));
    }

    /**
     * Permet d'obtenir une liste de mail contenant le contenu d'un mail, l'envoyeur et les destinataires
     * @return                  Une LinkedList contenant les divers Mail
     * @throws IOException      Si un problème est rencontré lors de la lecture des fichiers
     */
    public LinkedList<Email> getAllMail() throws IOException {

        LinkedList<String> fakeMails = DataReader.readFakeMail();
        LinkedList<String> allEmailAddress = DataReader.readMailAdresse();

        try {
            // Vérifie que le nombre d'adresses mail est suffisant
            if (allEmailAddress.size() / 3 < NB_GROUP) throw new IllegalArgumentException("Insufficient number of addresses");

            // Vérifie que les adresses mails sont utilisables
            checkAddressMail(allEmailAddress);
        }
        catch (IllegalArgumentException ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
            throw ex;
        }

        LinkedList<EmailGroup> emailGroups = new LinkedList<>();

        // Permet de choisir aléatoirement les envoyeurs, destinataires et le texte de l'email
        Collections.shuffle(fakeMails);
        Collections.shuffle(allEmailAddress);

        for(int i = 0; i  < allEmailAddress.size(); ++i) {
            if(i < NB_GROUP) {
                // Création des emailsGroup et de leur envoyeur
                emailGroups.add(new EmailGroup(allEmailAddress.get(i)));
            }
            else {
                // Ajout des destinataires
                emailGroups.get(i % NB_GROUP).addReceiver(allEmailAddress.get(i));
            }
        }

        LinkedList<Email> emails = new LinkedList<>();
        for(int i = 0; i < NB_GROUP; ++i) {
            // Création des emails
            emails.add(new Email(fakeMails.get(i % fakeMails.size()), emailGroups.get(i)));
        }

        return emails;
    }


    /**
     * Permet de vérifier que toutes les adresses mails sont valides
     * Un IllegalArgumentException est levé si une adresse est invalide
     * @param mailAddress   La liste des adresses mails
     */
    public void checkAddressMail(LinkedList<String> mailAddress) {

        for(String address : mailAddress) {
            Matcher matcher = ADDRESS_PATTERN.matcher(address);
            if(!matcher.find()) throw new IllegalArgumentException(address + " is an invalid address");
        }
    }
}
