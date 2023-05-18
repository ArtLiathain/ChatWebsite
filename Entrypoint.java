public class Entrypoint {
        public static void main(String[] args) {
                ClientServer serve = new ClientServer();
                ClientMessage client = new ClientMessage("TEST");
                ClientMessage client2 = new ClientMessage(" HOPE");

                Thread server = new Thread(serve);
                Thread clientThread = new Thread(client);

                Thread clientThread2 = new Thread(client2);

                server.start();

                clientThread.start();
                clientThread2.start();

                try {
                        clientThread.join();
                        clientThread2.join();

                        server.join();

                } catch (Exception e) {
                        // TODO: handle exception
                }

        }
}
