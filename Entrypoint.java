public class Entrypoint {
    public static void main(String[] args) {
<<<<<<< main
        DateClient dateClient = new DateClient();
        DateServer dateServer = new DateServer();

        Thread server = new Thread(dateServer);
        Thread client = new Thread(dateClient);

=======
        ClientMessage clientMessage = new ClientMessage();
        // ClientMessage clientMessage2 = new ClientMessage();
        ClientServer clientServer = new ClientServer();
        ClientReceive clientClient = new ClientReceive();

        Thread message = new Thread(clientMessage);
        // Thread message2 = new Thread(clientMessage2);
        Thread server = new Thread(clientServer);
        Thread client = new Thread(clientClient);

        message.start();
        // message2.start();
>>>>>>> local
        server.start();
        client.start();

        // threadC1.start();
        // threadP1.start();

        try {
<<<<<<< main
=======
            message.join();
            // message2.join();
>>>>>>> local
            server.join();
            client.join();

            // threadC1.join();
            // threadP1.join();
        } catch (Exception e) {
        }
    }
}
