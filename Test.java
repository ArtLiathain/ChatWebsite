import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Test {

    public static void main(String[] args) {
        ArrayList<String> sharedResults = new ArrayList<String>() {
        };
        ArrayList<Semaphore> semaphores = new ArrayList<Semaphore>() {
        };
        semaphores.add(new Semaphore(1));
        LeetCode leetCode = new LeetCode(
                "Given an array of integers nums and an integer target, return indices of the two numbers such that they add up to target.\nYou may assume that each input would have exactly one solution, and you may not use the same element twice. indexes in ascending order.\n You have an array nums with all the nums and a target int.\n set answer[0] = to the first index and set answer [1] to the second index",
                "[98,99]", "int target = 398;int[] answer = new int[2]; ");
        RunJava runJava = new RunJava("int x=5;System.out.println(x);", "HELP", semaphores, 0, sharedResults, leetCode);

        Thread runThread = new Thread(runJava);

        runThread.start();

        try {
            runThread.join();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}