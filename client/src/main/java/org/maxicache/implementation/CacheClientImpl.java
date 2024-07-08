package org.maxicache.implementation;

import org.maxicache.exception.CacheCallException;
import org.maxicache.exception.CacheCommandException;
import org.maxicache.exception.CacheClientException;
import org.maxicache.interfaces.CacheClient;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.maxicache.shared.GetCacheCall;
import org.maxicache.shared.SetCacheCall;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.regex.Pattern;

public class CacheClientImpl implements CacheClient {

    private final Socket socket;
    private final ObjectOutputStream out;
    private final BufferedReader in;

    final String PORT_PATTERN = "^((6553[0-5])|(655[0-2][0-9])|(65[0-4][0-9]{2})|(6[0-4][0-9]{3})|([1-5][0-9]{4})|([0-5]{0,5})|([0-9]{1,4}))$";
    final String IPV4_PATTERN = "^(([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])(\\.(?!$)|$)){4}$";
    private static final Logger logger = LogManager.getLogger(CacheClientImpl.class);

    public CacheClientImpl(String ip, int port) {
        validate(ip, port);
        this.socket = createSocket(ip, port);
        this.out = createOutputStream(this.socket);
        this.in = createInputStream(this.socket);
        Runtime.getRuntime().addShutdownHook(new Thread(this::closeConnection));
    }

    private Socket createSocket(String ip, int port) {
        try {
            Socket socket = new Socket(ip, port);
            socket.setKeepAlive(true);
            return socket;
        } catch (IOException e) {
            throw new CacheClientException("Could not obtain connection to the server", e);
        }
    }

    private ObjectOutputStream createOutputStream(Socket socket) {
        try {
            return new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new CacheClientException("Could not create output stream", e);
        }
    }

    private BufferedReader createInputStream(Socket socket) {
        try {
            return new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            throw new CacheClientException("Could not create input stream", e);
        }
    }

    private void validate(String ip, int port) {
        boolean isValidIp = Pattern.compile(IPV4_PATTERN).matcher(ip).matches();
        if (!isValidIp && !ip.equals("localhost")) {
            throw new CacheClientException("Ip is not in valid form!");
        }
        boolean isValidPort = Pattern.compile(PORT_PATTERN).matcher(String.valueOf(port)).matches();
        if (!isValidPort) {
            throw new CacheClientException("Port is not in valid form!");
        }
    }

    @Override
    public void set(String key, String value, int ttl) {
        if (ttl <= 0) {
            throw new CacheCommandException("Time to live cannot be negative!");
        }
        SetCacheCall call = new SetCacheCall(key, value, ttl);
        sendCall(call);
    }

    @Override
    public String get(String key) {
        GetCacheCall call = new GetCacheCall(key);
        return sendCall(call);
    }

    private String sendCall(Serializable call) {
        try {
            // Send message to the server
            out.writeObject(call);
            out.flush(); // Ensure data is sent

            // Receive response from the server
            String response = in.readLine();
            logger.debug(response);
            return response;
        } catch (IOException e) {
            throw new CacheCallException("Could not successfully send and receive the call to the server", e);
        }
    }

    private void closeConnection() {
        try {
            in.close();
            out.close();
            socket.close();
            logger.info("Connection closed successfully.");
        } catch (IOException e) {
            logger.error("Error closing resources", e);
            throw new CacheClientException("Error closing resources", e);
        }
    }

}
