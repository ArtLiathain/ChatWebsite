public class Entrypoint {
    public static void main(String[] args) {
        DateClient dateClient = new DateClient();
        DateServer dateServer = new DateServer();

        Thread server = new Thread(dateServer);
        Thread client = new Thread(dateClient);

        server.start();
        client.start();
        // threadC1.start();
        // threadP1.start();

        try {
            server.join();
            client.join();
            // threadC1.join();
            // threadP1.join();
        } catch (Exception e) {
        }
    }
}
