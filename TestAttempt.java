import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TestAttempt {
    SharedData sharedData = new SharedData();;
    LeetCode leetCode = new LeetCode(
            "Given an array of integers nums and an integer target, return indices of the two numbers such that they add up to target.You may assume that each input would have exactly one solution, and you may not use the same element twice. indexes in ascending order",
            "[98,99]", "int target = 398;int[] answer = new int[2]; ");
    RunJava runJava = new RunJava(
            "for (int i = 0; i < nums.size(); i++) {for (int j = i + 1; j < nums.size(); j++) {if (nums.get(i) + nums.get(j) == target) { answer[0] = i;answer[1] = j;}}}",
            "CRY", sharedData, leetCode, 1);

    @Test
    void TestCodegen() {

        Thread runThread = new Thread(runJava);

        runThread.start();

        try {
            runThread.join();
        } catch (Exception e) {
            // TODO: handle exception
        }
        System.out.println(sharedData.getSharedResults(0));
        assertEquals("[98,99]:*", sharedData.getSharedResults(0));
    }

}