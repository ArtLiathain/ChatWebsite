import java.net.*;
import java.util.Scanner;
import java.io.*;

public class ClientMessage implements Runnable {
    public void run() {
        Scanner s = new Scanner(System.in);
        System.out.println("Enter Message: ");
        String msg = s.nextLine();
        s.close();
        try {
            Socket sock = new Socket("127.0.0.1", 6013); // connect to the server

            PrintWriter pout = new PrintWriter(
                    sock.getOutputStream(), true);
            // write the message to the socket
            pout.println(msg);
            sock.close(); // close the socket
        } catch (IOException ioe) {
            System.err.println(ioe);
        }
    }
}
