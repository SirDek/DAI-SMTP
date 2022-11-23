package configuration;

import java.io.IOException;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) throws IOException {
        LinkedList<String> test = DataReader.readFakeMail();
        for (String s : test) {
            System.out.println(s);
        }
    }
}