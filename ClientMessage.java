import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class ClientMessage {

    String messageString;

    ClientMessage(String messageString) {
        this.messageString = messageString;
    }

    public static void main(String[] args) {
        Socket client;
        try {
            client = new Socket("127.0.0.1", 1234);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(client.getOutputStream());
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
            InputStreamReader inputStreamReader = new InputStreamReader(client.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String codeConcatenated = "";
            ArrayList<String> code = new ArrayList<String>();
            Scanner inputScanner = new Scanner(System.in);

            while (inputScanner.hasNextLine()) {
                String line = inputScanner.nextLine();
                if (line.equals("redo")) {
                    code.clear();
                    line = "";
                }
                if (line.equals("submit")) {
                    for (int i = 0; i < code.size(); i++) {
                        codeConcatenated = codeConcatenated + code.get(i);
                    }

                    break;
                }

                code.add(line);

            }
            // System.out.println(code);
            // System.out.println(codeConcatenated);

            bufferedWriter.write(codeConcatenated);
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
