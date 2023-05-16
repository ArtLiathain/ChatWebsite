
import java.net.*;
import java.io.*;

public class ClientServer implements Runnable {

    public void run() {
        try {
            ServerSocket sock = new ServerSocket(6013);
            // now listen for connections
            while (true) {
                Socket client = sock.accept();
                // we have a connection

                // CODE FOR RECEIVING MESSAGE
                InputStream in = client.getInputStream();
                BufferedReader bin = new BufferedReader(new InputStreamReader(in));// read message from the socket
                String line;// assert received message to line variable
                StringBuilder message = new StringBuilder(); // message variable to store line after closing client
                while ((line = bin.readLine()) != null)
                    message.append(line); // append line to message

                client.close(); // close client

                Socket client2 = sock.accept(); // open new client, which should now connect to "ClientReceive.java"
                PrintWriter pout = new PrintWriter(
                        client2.getOutputStream(), true);
                // write the message to the socket
                pout.println(message.toString());
                // close the socket and resume listening for more connections
                client2.close();
            }
        } catch (IOException ioe) {
            System.err.println(ioe);
        }
    }
}
