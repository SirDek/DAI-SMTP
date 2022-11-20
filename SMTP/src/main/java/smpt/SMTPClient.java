package smpt;
import donnee.*;

import java.io.*;
import java.net.Socket;

public class SMTPClient {



    public void send(Mail mail) throws IOException {

        final String SMTPADDR = "";//mail.getaddr
        final int port = 0; //mail.getport

        final String SEND = "\r\n";
        final String FORMAT = "UTF-8";
        final String ERROR_MSG = "ERROR with message recieved : ";

        Socket socket = new Socket(SMTPADDR, port);

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), FORMAT));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), FORMAT));
        String line = in.readLine();

        out.write("EHLO mock");
        out.flush();

        line = in.readLine();
        if (!line.startsWith("250")) {
            throw new IOException(ERROR_MSG + line);
        }

        while(line.startsWith("250-")) {
            line = in.readLine();
        }

        out.write("MAIL FROM:");// + adresse d'envoi);
        out.flush();

        line = in.readLine();
        if (!line.startsWith("250")) {
            throw new IOException(ERROR_MSG + line);
        }

        /*for (Mail reciever : mail.reciever) {
            out.write("RCPT TO:");// + mail.sender);
            out.flush();
            line = in.readLine();
            if (!line.startsWith("250")) {
                throw new IOException(ERROR_MSG + line);
            }
        }*/

        out.write("DATA");
        out.flush();
        if (line.startsWith("4") || line.startsWith("5")) {
            throw new IOException(ERROR_MSG + line);
        }

        //out.write(//joke);
        //out.write(".\r\n");
        line = in.readLine();
        if (!line.startsWith("250")) {
            throw new IOException(ERROR_MSG + line);
        }

    }

}
