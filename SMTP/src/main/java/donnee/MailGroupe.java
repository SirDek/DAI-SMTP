package donnee;

import java.util.ArrayList;

public class MailGroupe {
    private String receiver;
    private ArrayList<String> destinataire;

    public MailGroupe(String receiver, ArrayList<String> destinataire) {
        this.receiver = receiver;
        this.destinataire = destinataire;
    }

    public String getReceiver() {
        return receiver;
    }

    public ArrayList<String> getDestinataire() {
        return destinataire;
    }
}
