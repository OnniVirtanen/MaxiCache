package org.maxicache;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.maxicache.shared.CacheCall;
import org.maxicache.shared.GetCacheCall;
import org.maxicache.shared.SetCacheCall;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class RequestHandler extends Thread {

    private final Socket socket;
    private final DataStore dataStore;
    private static final Logger logger = LogManager.getLogger(RequestHandler.class);

    RequestHandler(Socket socket) {
        this.socket = socket;
        this.dataStore = InMemoryDataStore.getInstance();
    }

    @Override
    public void run() {
        try {
            logger.debug("Received a connection");

            // Get input and output streams
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            while (!socket.isClosed()) {
                try {
                    CacheCall call = (CacheCall) in.readObject();

                    // Read message from client
                    if (call.getName().equals("set")) {
                        SetCacheCall setCall = (SetCacheCall) call;
                        dataStore.set(setCall.getKey(), setCall.getValue(), setCall.getTtl());
                        logger.info("client request: ", setCall.getName(), setCall.getKey(), setCall.getValue(), setCall.getTtl());
                        out.println("success");
                    } else if (call.getName().equals("get")) {
                        GetCacheCall getCall = (GetCacheCall) call;
                        logger.info("client request: ", getCall.getName(), getCall.getKey());
                        out.println(dataStore.get(getCall.getKey()));
                    }

                    out.flush();
                } catch (EOFException e) {
                    // Client has closed the connection
                    logger.debug("Client disconnected.");
                    break;
                } catch (IOException e) {
                    logger.error("IOException", e);
                    break;
                } catch (ClassNotFoundException e) {
                    logger.error("Class not found", e);
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

