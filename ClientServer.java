import java.net.*;
import java.util.ArrayList;
import java.io.*;

public class ClientServer {

    public static void main(String[] args) {

        ServerSocket serverSocket = null;
        ArrayList<String> testArray = new ArrayList<String>() {
        };
        try {
            serverSocket = new ServerSocket(1234);

            while (true) {

                Socket clienSocket = serverSocket.accept();

                InputStreamReader inputStreamReader = new InputStreamReader(clienSocket.getInputStream());

                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(clienSocket.getOutputStream());
                BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

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
