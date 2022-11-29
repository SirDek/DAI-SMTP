package data;

import java.util.LinkedList;

/**
 * Classe Mail
 * Cette classe a pour but de stocker le contenu d'un email, l'envoyeur et les destinataires
 * @author Laetitia Guidetti
 * @author Cédric Centeno
 * Date : 25.11.2022
 */
public class Email {
    private final String FAKE_EMAIL;
    private final EmailGroup EMAIL_GROUP;

    /**
     * Constructeur d'Email
     * @param fakeEmail      Le contenu du mail
     * @param emailGroup    Le groupe d'adresse contenant l'envoyeur et les destinataires
     */
    public Email(String fakeEmail, EmailGroup emailGroup) {
        this.FAKE_EMAIL = fakeEmail;
        this.EMAIL_GROUP = emailGroup;
    }

    /**
     * Permet d'obtenir le contenu du mail
     * @return      Le contenu du mail
     */
    public String getFakeEmail() {
        return FAKE_EMAIL;
    }

    /**
     * Permet d'obtenir l'envoyeur
     * @return      L'adresse de l'envoyeur
     */
    public String getSender() {
        return EMAIL_GROUP.getSender();
    }

    /**
     * Permet d'obtenir la liste des destinataires
     * @return      La LinkedList contenant les destinataires
     */
    public LinkedList<String> getReceivers() {
        return EMAIL_GROUP.getReceivers();
    }

}
