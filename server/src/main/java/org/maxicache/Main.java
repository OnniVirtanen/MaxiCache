package org.maxicache;

public class Main {
    public static void main(String[] args) {
        int port = 6379;
        SocketServer server = new SocketServer(port);
        server.startServer();

        // Automatically shutdown in 1 minute
        int minute = 1000;
        try {
            Thread.sleep(minute);
        } catch (Exception e) {
            e.printStackTrace();
        }

        server.stopServer();
    }
}
