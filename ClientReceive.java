import java.net.*;
import java.io.*;

public class ClientReceive implements Runnable {
    public void run() {
        try {

            Socket sock = new Socket("127.0.0.1", 6013); // connect to the server
            InputStream in = sock.getInputStream();// get input stream of the socket
            BufferedReader bin = new BufferedReader(new InputStreamReader(in));// read message from the socket
            String line;// store the message

            while ((line = bin.readLine()) != null)
                System.out.println("CLIENT RECEIVED: " + line);// print to terminal. (Added "CLIENT RECEIVED: "
                                                               // so I could tell it was the right class printing)

            sock.close();
        } catch (IOException ioe) {
            System.err.println(ioe);
        }
    }
}
