import Project.DateClient;
import Project.DateServer;

public class Entrypoint {
    public static void main(String[] args) {
        DateClient dateClient = new DateClient();
        DateServer dateServer = new DateServer();

        Thread server = new Thread(DateServer);
        Thread client = new Thread(dateClient);

        threadC.start();
        threadP.start();
        // threadC1.start();
        // threadP1.start();

        try {
            threadC.join();
            threadP.join();
            // threadC1.join();
            // threadP1.join();
        } catch (Exception e) {
        }
        System.out.println("count = " + boundedBuffer.getArray());

    }
}
