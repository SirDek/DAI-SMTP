package configuration;

import donnee.*;

import java.io.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Properties;

public class Config {

    final static private String CONFIG_PATH = "config.properties";
    public Server createServer() throws IOException {
        InputStream input = Config.class.getClassLoader().getResourceAsStream(CONFIG_PATH);
        Properties prop = new Properties();
        prop.load(input);
        return new Server(prop.getProperty("host"), Integer.parseInt(prop.getProperty("port")));
    }

    public LinkedList<Mail> getAllMail() throws IOException {

        LinkedList<String> fakeMails = DataReader.readFakeMail();
        LinkedList<String> mailAdress = DataReader.readMailAdresse();

        if (mailAdress.size() / 3 < fakeMails.size()) throw new IOException();

        final int NB_GROUPE = fakeMails.size();
        LinkedList<MailGroupe> mailGroupes = new LinkedList<>();
        Collections.shuffle(mailAdress);

        for(int i = 0; i  < mailAdress.size(); ++i) {
            if(i < NB_GROUPE) {
                mailGroupes.add(new MailGroupe(mailAdress.get(i)));
            }
            else {
                mailGroupes.get(i % NB_GROUPE).addReceiver(mailAdress.get(i));
            }
        }

        LinkedList<Mail> mails = new LinkedList<>();
        for(int i = 0; i < NB_GROUPE; ++i) {
            mails.add(new Mail(fakeMails.get(i), mailGroupes.get(i)));
        }

        return mails;
    }
}
