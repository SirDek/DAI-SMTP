package configuration;

import donnee.*;

import java.io.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Config {

    final static private String CONFIG_PATH = "config.properties";
    final static private Pattern ADDRESS_PATTERN = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    final int NB_GROUPE;
    private Properties prop;


    Config() throws IOException {
        InputStream input = Config.class.getClassLoader().getResourceAsStream(CONFIG_PATH);
        prop = new Properties();
        prop.load(input);
        NB_GROUPE = Integer.parseInt(prop.getProperty("nbGroupe"));
    }
    public ServerInfo createServer() {
        return new ServerInfo(prop.getProperty("host"), Integer.parseInt(prop.getProperty("port")));
    }

    public LinkedList<Mail> getAllMail() throws IOException {

        LinkedList<String> fakeMails = DataReader.readFakeMail();
        LinkedList<String> mailAdress = DataReader.readMailAdresse();

        if (mailAdress.size() / 3 < NB_GROUPE) throw new IOException();

        checkAddressMail(mailAdress);

        final int NB_GROUPE = fakeMails.size();
        LinkedList<MailGroupe> mailGroupes = new LinkedList<>();

        Collections.shuffle(fakeMails);
        Collections.shuffle(mailAdress);

        for(int i = 0; i  < mailAdress.size(); ++i) {
            if(i < NB_GROUPE) {
                mailGroupes.add(new MailGroupe(mailAdress.get(i % NB_GROUPE)));
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

    public void checkAddressMail(LinkedList<String> mailaddress) throws IOException {

        for(String address : mailaddress) {
            Matcher matcher = ADDRESS_PATTERN.matcher(address);
            if(!matcher.find()) throw new IOException();
        }
    }
}
