package org.maxicache;

public class Main {
    public static void main(String[] args) {
        int port = 6379;
        SocketServer server = new SocketServer(port);
        server.startServer();

        // Automatically shutdown in 60 minutes
        int hour = 3_600_000;
        try {
            Thread.sleep(hour);
        } catch (Exception e) {
            e.printStackTrace();
        }

        server.stopServer();
    }
}
