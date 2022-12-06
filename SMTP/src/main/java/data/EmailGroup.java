package data;

import java.util.LinkedList;

/**
 * Classe EmailGroup
 * Cette classe a pour but de stocker un groupe d'adresse contenant des destinataires et un envoyeur
 * @author Laetitia Guidetti
 * @author Cédric Centeno
 * Date : 06.12.2022
 */
public class EmailGroup {
    private final String SENDER;
    private final LinkedList<String> RECEIVERS;

    /**
     * Constructeur de MailGroupe
     * @param sender     L'adresse de l'envoyeur
     */
    public EmailGroup(String sender) {
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
     * Permet d'obtenir la liste de tous les destinataires
     * @return  La LinkedList contenant tous les destinataires
     */
    public LinkedList<String> getReceivers() {
        return RECEIVERS;
    }
}
