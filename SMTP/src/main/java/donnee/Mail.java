package donnee;

import java.util.LinkedList;

/**
 * Classe Mail
 * Cette classe a pour but de stocker le contenu d'un mail, l'envoyeur et les destinataires
 * @author Laetitia Guidetti
 * @author Cédric Centeno
 * Date : 25.11.2022
 */
public class Mail {
    private String fakeMail;
    private MailGroupe mailGroupe;

    /**
     * Constructeur de Mail
     * @param fakeMail      Le contenu du mail
     * @param mailGroupe    Le groupe d'adresse contenant l'envoyeur et les destinataires
     */
    public Mail(String fakeMail, MailGroupe mailGroupe) {
        this.fakeMail = fakeMail;
        this.mailGroupe = mailGroupe;
    }

    /**
     * Permet d'obtenir le contenu du mail
     * @return      Le contenu du mail
     */
    public String getFakeMail() {
        return fakeMail;
    }

    /**
     * Permet d'obtenir l'envoyeur
     * @return      L'adresse de l'envoyeur
     */
    public String getSender() {
        return mailGroupe.getSender();
    }

    /**
     * Permet d'obtenir la liste des destinataires
     * @return      La LinkedList contenant les destinataires
     */
    public LinkedList<String> getReceivers() {
        return mailGroupe.getReceivers();
    }

}
