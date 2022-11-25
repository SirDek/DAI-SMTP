package donnee;

import java.util.LinkedList;

/**
 * Classe MailGroupe
 * Cette classe a pour but de stocker un groupe d'adresse contenant des destinataires et un envoyaeur
 * @author Laetitia Guidetti
 * @author Cédric Centeno
 * Date : 25.11.2022
 */
public class MailGroupe {
    private final String SENDER;
    private final LinkedList<String> RECEIVERS;

    /**
     * Constructeur de MailGroupe
     * @param sender     L'adresse de l'envoyeur
     */
    public MailGroupe(String sender) {
        SENDER = sender;
        RECEIVERS = new LinkedList<>();
    }

    /**
     * Permet d'ajouter un nouveau destinataire
     * @param receiver   L'adresse du nouveau destinataire
     */
    public void addReceiver(String receiver) {
        RECEIVERS.add(receiver);
    }

    /**
     * Permet d'obtenir l'adresse de l'envoyeur
     * @return      L'envoyeur
     */
    public String getSender() {
        return SENDER;
    }

    /**
     * Permet d'obtenir la liste de tout les destinataires
     * @return  La LinkedList contenant tout les destinataires
     */
    public LinkedList<String> getReceivers() {
        return RECEIVERS;
    }
}
