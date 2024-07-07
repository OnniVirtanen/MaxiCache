package org.maxicache;

public class Main {
    public static void main(String[] args) {
        int port = 6379;
        SocketServer server = new SocketServer(port);
        server.startServer();

        // Add a shutdown hook to stop the server when the JVM is shutting down
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                server.stopServer();
                System.out.println("Server stopped due to JVM shutdown.");
            }
        }));

        // Keep the main thread running
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
