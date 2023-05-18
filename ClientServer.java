import java.net.*;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.io.*;

public class ClientServer {

    public static void main(String[] args) {

        ServerSocket serverSocket = null;
        ArrayList<String> testArray = new ArrayList<String>() {
        };
        ArrayList<Thread> threadsToRun = new ArrayList<Thread>() {
        };
        ArrayList<Semaphore> semaphores = new ArrayList<Semaphore>() {
        };
        ArrayList<String> sharedResults = new ArrayList<String>() {
        };
        LeetCode leetCode = new LeetCode(
                "Given an array of integers nums and an integer target, return indices of the two numbers such that they add up to target.You may assume that each input would have exactly one solution, and you may not use the same element twice. indexes in ascending order",
                "[1,2]", "int target = 9;int[] answer = new int[2]{}; ");
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

                for (int i = 0; i < testArray.size(); i++) {
                    semaphores.add(new Semaphore(0));
                }
                semaphores.get(0).notify();
                int count = 0;
                for (int i = 0; i < testArray.size(); i++) {
                    threadsToRun.add(new Thread(
                            new RunJava(testArray.get(i), "Thread" + count, semaphores, count, sharedResults,
                                    leetCode)));
                    count += 1;
                    threadsToRun.get(i).start();
                }

                try {
                    for (Thread thread : threadsToRun) {
                        thread.join();
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                }

                int maxIndex = 0;
                double max = 2000;
                for (int i = 0; i < sharedResults.size(); i++) {
                    String[] answers = sharedResults.get(i).split(":");
                    if (Double.parseDouble(answers[1]) < max && answers[0].equals(leetCode.getAnswer())) {
                        max = Double.parseDouble(answers[1]);
                        maxIndex = i;
                    }
                }

                bufferedWriter.write("all");
                bufferedWriter.newLine();
                bufferedWriter.flush();

            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
