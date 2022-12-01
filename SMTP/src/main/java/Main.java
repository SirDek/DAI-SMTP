import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

import configuration.Config;
import configuration.DataReader;
import data.*;
import smpt.SMTPClient;

public class Main {
    final static Logger LOG = Logger.getLogger(DataReader.class.getName());

    public static void main(String[] args) {
        try {
            Config config = new Config();

            ServerInfo serv = config.createServer();
            System.out.println(serv.getPort() + " " + serv.getHost());

            LinkedList<Email> allEmails = config.getAllMail();
            for (Email email : allEmails) {
                SMTPClient client = new SMTPClient(email, serv);
                client.send();
                /*
                System.out.println("Sender : " + email.getSender());
                for (String s : email.getReceivers()) {
                    System.out.println("Receivers : " + s);
                }
                System.out.println("Mail : " + email.getFakeEmail() + " \n\n\n");*/
            }
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Fatal error - End of program");
        }
    }
}