import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class ClientHandler implements Runnable {

    // ARTS STUFF
    ArrayList<Thread> threadsToRun = new ArrayList<Thread>() {
    };
    ArrayList<Semaphore> semaphores = new ArrayList<Semaphore>() {
    };
    SharedData sharedResults = new SharedData();
    ArrayList<String> users = new ArrayList<String>() {
    };
    ArrayList<String> codes = new ArrayList<String>() {
    };
    LeetCode leetCode = new LeetCode(
            "Given an array of integers nums and an integer target, return indices of the two numbers such that they add up to target.You may assume that each input would have exactly one solution, and you may not use the same element twice. indexes in ascending order",
            "[1,2]", "int target = 9;int[] answer = new int[2]{}; ");

    // END OF ARTS STUFF

    public static ArrayList<ClientHandler> clienthandlers = new ArrayList<>();

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
                if (codeFromClient.equals("ready")) {
                    if (!clientReady) {
                        clientReady = true;
                        if (checkReady()) {
                            broadcast(leetCode.getQuestion());
                        }
                    }
                    clientReady = true;
                }
                if (codeFromClient.substring(codeFromClient.length() - 1).equals("}")) {
                    if (checkReady()) {
                        clientSubmitted = true;
                    }
                    if (checkSubmitted()) {
                        getSubmissions();
                        runAllSubmissions();
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
            codes.add(clientHandler.codeFromClient);
            users.add(clientHandler.clientUsername);
        }
    }

    public void runAllSubmissions() {
        broadcast("Got here");
        semaphores.add(new Semaphore(1));
        for (int i = 1; i < codes.size(); i++) {
            broadcast("all assigned");
            semaphores.add(new Semaphore(0));
        }
        broadcast("Got here");
        int count = 0;
        for (int i = 0; i < codes.size(); i++) {
            threadsToRun.add(new Thread(
                    new RunJava(codes.get(i), "Thread" + count, semaphores, count, sharedResults,
                            leetCode)));
            count += 1;
            threadsToRun.get(i).start();
        }
        broadcast("Got here");

        try {
            for (Thread thread : threadsToRun) {
                thread.join();
            }
        } catch (Exception e) {
            broadcast("THIS BE THE ISSUE");
        }
        broadcast("Got here");
        int lowIndex = 0;
        double low = 2000;
        broadcast(sharedResults.getSharedResults().get(0));
        for (int i = 0; i < codes.size(); i++) {
            String[] answers = sharedResults.getSharedResults().get(i).split(":");
            if (Double.parseDouble(answers[1]) < low && answers[0].equals(leetCode.getAnswer())) {
                low = Double.parseDouble(answers[1]);
                lowIndex = i;
            }
        }

        broadcast(users.get(lowIndex) + "has won with a time of " + low);

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
