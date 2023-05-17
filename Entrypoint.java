public class Entrypoint {
    public static void main(String[] args) {
        ClientMessage clientMessage = new ClientMessage();
        ClientServer clientServer = new ClientServer();
        ClientReceive clientClient = new ClientReceive();

        Thread message = new Thread(clientMessage);
        Thread server = new Thread(clientServer);
        Thread client = new Thread(clientClient);

        message.start();
        server.start();
        client.start();
        // threadC1.start();
        // threadP1.start();

        try {
            message.join();
            server.join();
            client.join();
            // threadC1.join();
            // threadP1.join();
        } catch (Exception e) {
        }
    }
}