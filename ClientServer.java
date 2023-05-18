
import java.net.*;
import java.util.ArrayList;
import java.io.*;

public class ClientServer implements Runnable {
    ArrayList<String> arrayOfMessages = new ArrayList<String>() {
    };

    public void run() {
        try {
            ServerSocket socketServer = new ServerSocket(6013);
            // now listen for connections
            while (true) {

                System.out.println("Waiting for Clients");
                Socket inputSocket = socketServer.accept();
                System.out.println("Clients Accepted");
                // we have a connection

                // CODE FOR RECEIVING MESSAGE
                InputStream inputStream = inputSocket.getInputStream();
                BufferedReader messageReader = new BufferedReader(new InputStreamReader(inputStream));// read message
                                                                                                      // from the socket

                String message;// assert received message to line variable
                System.out.println("Read message");
                // message variable to store line after closing client
                while ((message = messageReader.readLine()) != null)
                    arrayOfMessages.add(message); // append line to message
                System.out.println("Appended To list");

                inputSocket.close(); // close client
                System.out.println("OutputSocket");
                Socket outputSocket = socketServer.accept(); // open new client, which should now connect to
                System.out.println("Output Accepted"); // "ClientReceive.java"
                PrintWriter printOut = new PrintWriter(
                        outputSocket.getOutputStream(), true);
                String allMessages = "";
                System.out.println("Message Array");
                for (String oldMessage : arrayOfMessages) {
                    allMessages += oldMessage;
                }
                System.out.println("Ans" + allMessages);
                printOut.println(allMessages);
                // close the socket and resume listening for more connections
                outputSocket.close();

            }
        } catch (IOException ioe) {
            System.err.println(ioe.toString());
        }

    }
}
