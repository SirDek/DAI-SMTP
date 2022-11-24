package donnee;

import java.util.LinkedList;

public class MailGroupe {
    private String sender;
    private LinkedList<String> receivers;

    public MailGroupe() {

    }
    public MailGroupe(String sender) {
        this.sender = sender;
        receivers = new LinkedList<>();
    }

    public void addReceiver(String receiver) {
        receivers.add(receiver);
    }

    public String getSender() {
        return sender;
    }

    public LinkedList<String> getReceivers() {
        return receivers;
    }
}
