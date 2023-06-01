
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class RunJava implements Runnable {

    String ConcatenatedCode;
    String name;
    SharedData sharedData;
    LeetCode leetCode;
    int order;

    RunJava(String ConcatenatedCode, String name,
            SharedData sharedData, LeetCode leetCode, int order) {
        this.ConcatenatedCode = ConcatenatedCode;
        this.name = name;
        this.sharedData = sharedData;
        this.leetCode = leetCode;
        this.order = order;
    }

    @Override
    public void run() {
        try {

            String fileName = name + ".java";
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            writer.write(
                    "import java.util.*; import java.time.Duration;import java.time.LocalDateTime; import java.io.*;public class "
                            + name
                            + "{ public static void main(String[] args) throws FileNotFoundException { Scanner s = new Scanner(new File(\"SampleValues.txt\"));ArrayList<Integer> nums = new ArrayList<Integer>();while (s.hasNext()){nums.add(Integer.parseInt(s.next()));}s.close();LocalDateTime startTime = LocalDateTime.now(); "
                            + leetCode.getSetup()
                            + ConcatenatedCode
                            + "LocalDateTime endDateTime = LocalDateTime.now(); Duration duration = Duration.between(startTime, endDateTime);double time = duration.toMillis();System.out.println(\"[\" + answer[0] + \",\" + answer[1] + \"]\" + ':' + time); }}");
            writer.close();

            Runtime rt = Runtime.getRuntime();
            Process pr = rt.exec("javac .\\" + name + ".java");
            ProcessBuilder processBuilder = new ProcessBuilder("java", fileName);
            Process process = processBuilder.start();

            // Read the output from the process
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            String ans = "";

            while ((line = reader.readLine()) != null) {
                System.out.println(line);

                ans += line;
                System.out.println(ans);
            }
            sharedData.setSharedResults(order, ans);

            // Wait for the process to finish
            int exitCode = process.waitFor();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            sharedData.addData("Failed:2000");
            System.out.println("Code Failed to Compile");

        } catch (InterruptedException e) {
            sharedData.addData("Failed:2000");
            // TODO Auto-generated catch block
            System.out.println("Code Failed to Compile");

        }
    }
}
