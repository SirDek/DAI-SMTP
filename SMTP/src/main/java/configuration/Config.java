package configuration;

import donnee.*;

import java.io.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Properties;
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

    final static private String CONFIG_PATH = "config.properties";
    final static private Pattern ADDRESS_PATTERN = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    final int NB_GROUPE;
    private Properties prop;


    /**
     * Constructeur de Config
     * @throws IOException  Si impossible d'accéder aux configurations
     */
    Config() throws IOException {
        InputStream input = Config.class.getClassLoader().getResourceAsStream(CONFIG_PATH);
        prop = new Properties();
        prop.load(input);
        NB_GROUPE = Integer.parseInt(prop.getProperty("nbGroupe"));
    }

    /**
     * Permet d'obtenir un objet contenant les informations permettant de se connecter à un serveur
     * @return  Un objet de la classe ServerInfo
     */
    public ServerInfo createServer() {
        return new ServerInfo(prop.getProperty("host"), Integer.parseInt(prop.getProperty("port")));
    }

    /**
     * Permet d'obtenir une liste de mail contenant le contenu d'un mail, l'envoyeur et les destinataires
     * @return                  Une LinkedList contenant les divers Mail
     * @throws IOException      Si le nombre d'adresses n'est pas suffisant ou si une adresse mail n'est pas valide
     */
    public LinkedList<Mail> getAllMail() throws IOException {

        LinkedList<String> fakeMails = DataReader.readFakeMail();
        LinkedList<String> mailAdress = DataReader.readMailAdresse();

        // Vérifie que le nombre d'adresses mail est suffisant
        if (mailAdress.size() / 3 < NB_GROUPE) throw new IOException();

        // Vérifie que les adresses mails sont utilisables
        if(!checkAddressMail(mailAdress)) throw new IOException();

        LinkedList<MailGroupe> mailGroupes = new LinkedList<>();

        // Permet de choisir aléatoirement les envoyeurs, destinataires et le text du mail
        Collections.shuffle(fakeMails);
        Collections.shuffle(mailAdress);

        for(int i = 0; i  < mailAdress.size(); ++i) {
            if(i < NB_GROUPE) {
                // Création des mailsGroupe et de leur envoyeur
                mailGroupes.add(new MailGroupe(mailAdress.get(i)));
            }
            else {
                // Ajout des destinataires
                mailGroupes.get(i % NB_GROUPE).addReceiver(mailAdress.get(i));
            }
        }

        LinkedList<Mail> mails = new LinkedList<>();
        for(int i = 0; i < NB_GROUPE; ++i) {
            // Création des mails
            mails.add(new Mail(fakeMails.get(i), mailGroupes.get(i)));
        }

        return mails;
    }


    /**
     * Permet de vérifier que toutes les adresses mails sont valides
     * @param mailAddress   La liste des adresses mails
     * @return              True si toutes les adresses sont valides, false si au moins une est invalide
     */
    public boolean checkAddressMail(LinkedList<String> mailAddress) {

        for(String address : mailAddress) {
            Matcher matcher = ADDRESS_PATTERN.matcher(address);
            if(!matcher.find()) return false;
        }
        return true;
    }
}
