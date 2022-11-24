package configuration;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class DataReader {

    final static private String ADDRESS_PATH = "adresse.txt";
    final static private String FAKE_MAIL_PATH = "fakeMail.txt";
    final static private String SEPARATOR = "#";
    final static private String END_LINE = "\r\n";
    static ClassLoader classLoader = DataReader.class.getClassLoader();

    public static LinkedList<String> readMailAdresse() throws IOException {
        FileReader file = new FileReader(Objects.requireNonNull(classLoader.getResource(ADDRESS_PATH)).getFile());
        BufferedReader reader = new BufferedReader(file);

        LinkedList<String> mailAdresses = new LinkedList<>();
        String line;
        while((line = reader.readLine()) != null) {
            mailAdresses.add(line);
        }

        return mailAdresses;
    }

    public static LinkedList<String> readFakeMail() throws IOException {
        FileReader file = new FileReader(Objects.requireNonNull(classLoader.getResource(FAKE_MAIL_PATH)).getFile());
        BufferedReader reader = new BufferedReader(file);

        LinkedList<String> fakeMails = new LinkedList<>();
        String line;
        StringBuilder fakeMail = new StringBuilder();

        while((line = reader.readLine()) != null) {
            if(line.equals(SEPARATOR)) {
                fakeMails.add(fakeMail.toString());
                fakeMail = new StringBuilder();
            }
            else {
                fakeMail.append(line).append(END_LINE);
            }
        }
        fakeMails.add(fakeMail.toString());
        return fakeMails;
    }
}
