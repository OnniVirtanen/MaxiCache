package org.maxicache;

import org.maxicache.shared.CacheCall;
import org.maxicache.shared.GetCacheCall;
import org.maxicache.shared.SetCacheCall;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class RequestHandler extends Thread {

    private final Socket socket;
    private final DataStore dataStore;

    RequestHandler(Socket socket) {
        this.socket = socket;
        this.dataStore = new InMemoryDataStore();
    }

    @Override
    public void run() {
        try {
            System.out.println("Received a connection");

            // Get input and output streams
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            while (!socket.isClosed()) {
                try {
                    CacheCall call = (CacheCall) in.readObject();

                    // Read message from client
                    if (call.getName().equals("set")) {
                        SetCacheCall setCall = (SetCacheCall) call;
                        System.out.println("Client says: " + setCall.getKey() + setCall.getValue() + setCall.getTtl());
                        dataStore.set(setCall.getKey(), setCall.getValue(), setCall.getTtl());
                        out.println("successfull set call");
                    } else if (call.getName().equals("get")) {
                        GetCacheCall getCall = (GetCacheCall) call;
                        System.out.println("Client says: " + getCall.getKey());
                        out.println(dataStore.get(getCall.getKey()));
                    }

                    out.flush();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                    break;
                }
            }
            in.close();
            out.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

