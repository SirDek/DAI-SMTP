package donnee;

import java.util.LinkedList;

public class Mail {
    private String fakeMail;
    private MailGroupe mailGroupe;

    public Mail(String fakeMail, MailGroupe mailGroupe) {
        this.fakeMail = fakeMail;
        this.mailGroupe = mailGroupe;
    }

    public String getFakeMail() {
        return fakeMail;
    }

    public String getSender() {
        return mailGroupe.getSender();
    }

    public LinkedList<String> getReceivers() {
        return mailGroupe.getReceivers();
    }

}
