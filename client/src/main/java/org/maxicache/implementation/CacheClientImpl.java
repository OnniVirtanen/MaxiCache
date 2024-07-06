package org.maxicache.implementation;

import org.maxicache.exception.CacheCallException;
import org.maxicache.exception.CacheCommandException;
import org.maxicache.exception.CacheClientException;
import org.maxicache.interfaces.CacheClient;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.Socket;
import java.util.regex.Pattern;

public class CacheClientImpl implements CacheClient {

    private final Socket socket;
    final String PORT_PATTERN = "^((6553[0-5])|(655[0-2][0-9])|(65[0-4][0-9]{2})|(6[0-4][0-9]{3})|([1-5][0-9]{4})|([0-5]{0,5})|([0-9]{1,4}))$";
    final String IPV4_PATTERN = "^(([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])(\\.(?!$)|$)){4}$";
    private static final Logger logger = LogManager.getLogger(CacheClientImpl.class);


    public CacheClientImpl(String ip, int port) {
        validate(ip, port);
        this.socket = createSocket(ip, port);
    }

    private Socket createSocket(String ip, int port) {
        try {
            return new Socket(ip, port);
        } catch (IOException e) {
            throw new CacheClientException("Could not obtain connection to the server", e);
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
            // Setup output stream to send data to the server

            // Setup input stream to receive data from the server
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Send message to the server
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(call);

            // Receive response from the server
            String response = in.readLine();
            logger.debug(response);
            return response;
        } catch (IOException e) {
            throw new CacheCallException("Could not successfully send and receive the call to the server");
        }
    }

}
