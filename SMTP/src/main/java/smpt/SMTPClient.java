package smpt;
import donnee.*;

import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SMTPClient {
    final static Logger LOG = Logger.getLogger(SMTPClient.class.getName());

    Mail mail;
    ServerInfo server;
    public SMTPClient(Mail mail, ServerInfo server) {
        this.mail = mail;
        this.server = server;
    }

    public void send() {

        final String SMTPADDR = server.getHost();//mail.getaddr
        final int port = server.getPort(); //mail.getport

        final String SEND = ".\r\n";
        final String FORMAT = "UTF-8";
        final String ERROR_MSG = "ERROR with message recieved : ";

        Socket socket = null;
        BufferedReader in = null;
        BufferedWriter out = null;

        try {
            socket = new Socket(SMTPADDR, port);

            in = new BufferedReader(new InputStreamReader(socket.getInputStream(), FORMAT));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), FORMAT));
            String line = in.readLine();

            out.write("EHLO mock");
            out.flush();

            line = in.readLine();
            if (!line.startsWith("250")) {
                throw new IOException(ERROR_MSG + line);
            }

            while (line.startsWith("250-")) {
                line = in.readLine();
            }

            out.write("MAIL FROM:" + mail.getSender());// + adresse d'envoi);
            out.flush();

            line = in.readLine();
            if (!line.startsWith("250")) {
                throw new IOException(ERROR_MSG + line);
            }

            for (String reciever : mail.getReceivers()) {
                out.write("RCPT TO:" + reciever);// ;
                out.flush();
                line = in.readLine();
                if (!line.startsWith("250")) {
                    throw new IOException(ERROR_MSG + line);
                }
            }

            out.write("DATA");
            out.flush();
            if (line.startsWith("4") || line.startsWith("5")) {
                throw new IOException(ERROR_MSG + line);
            }

            out.write(mail.getFakeMail());
            out.write(SEND);
            line = in.readLine();
            if (!line.startsWith("250")) {
                throw new IOException(ERROR_MSG + line);
            }
        } catch (IOException ex) {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ex1) {
                    LOG.log(Level.SEVERE, ex.getMessage(), ex1);
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ex1) {
                    LOG.log(Level.SEVERE, ex.getMessage(), ex1);
                }
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException ex1) {
                    LOG.log(Level.SEVERE, ex1.getMessage(), ex1);
                }
            }
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
}
