
import java.net.*;
import java.io.*;

public class ClientServer implements Runnable{

    public void run() {
        try {
            ServerSocket sock = new ServerSocket(6013);
            // now listen for connections
            while (true) {
                Socket client = sock.accept();
                // we have a connection
                PrintWriter pout = new PrintWriter(
                        client.getOutputStream(), true);
                // write the Date to the socket
                pout.println(new java.util.Date().toString());
                // close the socket and resume listening for more connections
                client.close();
            }
        } catch (IOException ioe) {
            System.err.println(ioe);
        }
    }
}
