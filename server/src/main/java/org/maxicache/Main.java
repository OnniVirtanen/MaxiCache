package org.maxicache;

public class Main {
    public static void main(String[] args) {
        int port = 6379;
        SocketServer server = new SocketServer(port);
        server.startServer();

        // Automatically shutdown in 1 minute
        try {
            Thread.sleep(60000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        server.stopServer();
    }
}
