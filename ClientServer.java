import java.net.*;
import java.util.ArrayList;
import java.io.*;

public class ClientServer implements Runnable {

    public void run() {
        ServerSocket serverSocket = null;
        ArrayList<String> testArray = new ArrayList<String>() {
        };
        try {
            serverSocket = new ServerSocket(1234);

            while (true) {
                System.out.println("Basic stuff");
                Socket clienSocket = serverSocket.accept();
                System.out.println("Accepted");
                InputStreamReader inputStreamReader = new InputStreamReader(clienSocket.getInputStream());
                System.out.println("got here");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(clienSocket.getOutputStream());
                BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                System.out.println("even got here");
                String temp = bufferedReader.readLine();
                testArray.add(temp);
                String all = "";
                for (String string : testArray) {

                    all += string;
                }

                bufferedWriter.write(all);
                bufferedWriter.newLine();
                bufferedWriter.flush();

            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}