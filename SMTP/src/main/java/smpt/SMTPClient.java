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

        final String SMTP_HOST = server.getHost();//mail.getaddr
        final int port = server.getPort(); //mail.getport

        final String END_LINE = "\r\n";
        final String FORMAT = "UTF-8";
        final String ERROR_MSG = "ERROR with message recieved : ";

        Socket socket = null;
        BufferedReader in = null;
        BufferedWriter out = null;
        try {
            socket = new Socket(SMTP_HOST, port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream(), FORMAT));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), FORMAT));
            String line = in.readLine();
            out.write("EHLO mock" + END_LINE);
            out.flush();
            line = in.readLine();
            if (!line.startsWith("250")) {
                throw new IOException(ERROR_MSG + line);
            }

            while (line.startsWith("250-")) {
                line = in.readLine();
            }

            out.write("MAIL FROM:" + mail.getSender() + END_LINE);// + adresse d'envoi);
            out.flush();
            line = in.readLine();
            if (!line.startsWith("250")) {
                throw new IOException(ERROR_MSG + line);
            }

            for (String reciever : mail.getReceivers()) {
                out.write("RCPT TO:" + reciever + END_LINE);// ;
                out.flush();
                line = in.readLine();
                if (!line.startsWith("250")) {
                    throw new IOException(ERROR_MSG + line);
                }
            }

            out.write("DATA" + END_LINE);
            out.flush();
            line = in.readLine();
            if (line.startsWith("4") || line.startsWith("5")) { // todo faire mieux
                throw new IOException(ERROR_MSG + line);
            }
            // entêtes email
            out.write("From: " + mail.getSender() + END_LINE);
            out.write("To: ");
            for (String reciever : mail.getReceivers()) {
                out.write( reciever + " ");
            }

            //out.write("From: " + mail.getSubject() + END_LINE);

            out.write(END_LINE + END_LINE);
            out.write(mail.getFakeMail());
            out.write('.' +END_LINE);
            out.flush();

            line = in.readLine();
            if (!line.startsWith("250")) {
                throw new IOException(ERROR_MSG + line);
            }

            in.close();
            out.close();
            socket.close();

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
