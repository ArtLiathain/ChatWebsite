import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {

    public static ArrayList<ClientHandler> clienthandlers = new ArrayList<>();
    public static ArrayList<String> submissions = new ArrayList<>();

    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String clientUsername;
    private Boolean clientReady = false;
    public Boolean clientSubmitted = false;
    public String codeFromClient = "";

    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.clientUsername = bufferedReader.readLine();
            clienthandlers.add(this);
        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    @Override
    public void run() {

        while (socket.isConnected()) {
            try {
                codeFromClient = bufferedReader.readLine();
                if (codeFromClient.equals(clientUsername + "$ready")) {
                    if (!clientReady) {
                        clientReady = true;
                        if (checkReady()) {
                            broadcast("OH MY GOD YE ARE ALL GOOD TO GO");
                        }
                    }
                    clientReady = true;
                }
                if (codeFromClient.substring(codeFromClient.length() - 1).equals(";")) {
                    if (checkReady()) {
                        clientSubmitted = true;
                    }
                    if (checkSubmitted()) {
                        getSubmissions();

                        for (int i = 0; i < submissions.size(); i++) {
                            broadcast(submissions.get(i));
                        }
                    }
                }

            } catch (IOException e) {
                closeEverything(socket, bufferedReader, bufferedWriter);
                break;
            }
        }
    }

    public Boolean checkSubmitted() {
        int numClients = clienthandlers.size();
        int numSubmitted = 0;
        for (ClientHandler clientHandler : clienthandlers) {
            if (clientHandler.clientSubmitted.equals(true)) {
                numSubmitted++;
            }
        }
        if (numSubmitted == numClients) {
            return true;
        }
        return false;
    }

    public void getSubmissions() {
        for (ClientHandler clientHandler : clienthandlers) {
            submissions.add(clientHandler.codeFromClient);
        }
    }

    public Boolean checkReady() {
        int numClients = clienthandlers.size();
        int numReady = 0;
        for (ClientHandler clientHandler : clienthandlers) {
            if (clientHandler.clientReady.equals(true)) {
                numReady++;
            }
        }
        if (numReady == numClients) {
            return true;
        }
        return false;
    }

    public void broadcast(String message) {
        for (ClientHandler clientHandler : clienthandlers) {
            try {
                // if (!clientHandler.clientUsername.equals(clientUsername)) {
                // clientHandler.bufferedWriter.write(message);
                // clientHandler.bufferedWriter.newLine();
                // clientHandler.bufferedWriter.flush();
                // }
                clientHandler.bufferedWriter.write(message);
                clientHandler.bufferedWriter.newLine();
                clientHandler.bufferedWriter.flush();
            } catch (IOException e) {
                closeEverything(socket, bufferedReader, bufferedWriter);
            }
        }
    }

    public void removeClientHandler() {
        clienthandlers.remove(this);
        broadcast("SERVER: " + clientUsername + " bowed out.");
    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        removeClientHandler();
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
