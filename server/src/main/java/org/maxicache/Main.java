package org.maxicache;

public class Main {
    public static void main(String[] args) {
        int port = 6379;
        SocketServer server = new SocketServer(port);
        server.startServer();

        // Automatically shutdown in 1 minute
        int minutes = 60 * 10000;
        try {
            Thread.sleep(minutes);
        } catch (Exception e) {
            e.printStackTrace();
        }

        server.stopServer();
    }
}
