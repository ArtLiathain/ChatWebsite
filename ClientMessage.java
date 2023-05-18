import java.net.*;
import java.io.*;

public class ClientMessage implements Runnable {

    String messageString;

    ClientMessage(String messageString) {
        this.messageString = messageString;
    }

    public void run() {
        Socket client;
        try {
            client = new Socket("127.0.0.1", 1234);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(client.getOutputStream());
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
            InputStreamReader inputStreamReader = new InputStreamReader(client.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            bufferedWriter.write(messageString);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            System.out.println(bufferedReader.readLine());

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
