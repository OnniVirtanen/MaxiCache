package org.maxicache;

import org.maxicache.implementation.SetCacheCall;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // create a server socket on port number 6379
        ServerSocket serverSocket = new ServerSocket(6379);
        System.out.println("Server is running and waiting for client connection...");

        // Accept incoming client connection
        Socket clientSocket = serverSocket.accept();
        System.out.println("Client connected!");

        // Setup input and output streams for communication with the client
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

        ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
        SetCacheCall setCacheCall = (SetCacheCall) in.readObject();

        // Read message from client
        System.out.println("Client says: " + setCacheCall.getKey() +setCacheCall.getValue() + setCacheCall.getTtl());

        // Send response to the client
        out.println("Message received by the server.");

        // Close the client socket
        clientSocket.close();
        // Close the server socket
        serverSocket.close();
    }
}