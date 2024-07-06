package org.maxicache;

import org.maxicache.shared.CacheCall;
import org.maxicache.shared.GetCacheCall;
import org.maxicache.shared.SetCacheCall;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class RequestHandler extends Thread {

    private Socket socket;

    RequestHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            System.out.println("Received a connection");

            // Get input and output streams
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream());

            CacheCall call = (CacheCall) in.readObject();

            // Read message from client
            if (call.getName().equals("set")) {
                SetCacheCall setCall = (SetCacheCall) call;
                System.out.println("Client says: " + setCall.getKey() + setCall.getValue() + setCall.getTtl());
            } else if (call.getName().equals("get")) {
                GetCacheCall getCall = (GetCacheCall) call;
                System.out.println("Client says: " + getCall.getKey());
            }

            out.print("successfull call");

            // Close our connection
            in.close();
            out.close();
            socket.close();

            System.out.println("Connection closed");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}