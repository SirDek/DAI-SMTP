package configuration;

import java.io.IOException;
import java.util.LinkedList;
import donnee.*;
public class Main {
    public static void main(String[] args) throws IOException {

        Config config = new Config();

        Server serv = config.createServer();
        System.out.println(serv.getPort() + " " + serv.getHost());
        LinkedList<Mail> allMails = config.getAllMail();
        for (Mail mail : allMails) {
            System.out.println("Sender : " + mail.getSender());
            for (String s : mail.getReceivers()) {
                System.out.println("Receivers : " + s);
            }
            System.out.println("Mail : " + mail.getFakeMail() + " \n\n\n");
        }
    }
}