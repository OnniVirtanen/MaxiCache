package org.maxicache.implementation;

import org.maxicache.exception.CommandException;
import org.maxicache.exception.NodeException;
import org.maxicache.interfaces.CacheClient;

import java.io.IOException;
import java.net.Socket;
import java.util.regex.Pattern;

public class Node implements CacheClient {

    private final Socket socket;
    final String PORT_PATTERN = "^((6553[0-5])|(655[0-2][0-9])|(65[0-4][0-9]{2})|(6[0-4][0-9]{3})|([1-5][0-9]{4})|([0-5]{0,5})|([0-9]{1,4}))$";
    final String IPV4_PATTERN = "^(([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])(\\.(?!$)|$)){4}$";

    public Node(String ip, int port) {
        validate(ip, port);
        this.socket = createSocket(ip, port);
    }

    private Socket createSocket(String ip, int port) {
        try {
            return new Socket(ip, port);
        } catch (IOException e) {
            throw new NodeException("Could not obtain connection to the server", e);
        }
    }

    private void validate(String ip, int port) {
        boolean isValidIp = Pattern.compile(IPV4_PATTERN).matcher(ip).matches();
        if (!isValidIp && !ip.equals("localhost")) {
            throw new NodeException("Ip is not in valid form!");
        }
        boolean isValidPort = Pattern.compile(PORT_PATTERN).matcher(String.valueOf(port)).matches();
        if (!isValidPort) {
            throw new NodeException("Port is not in valid form!");
        }
    }

    @Override
    public void set(String key, String value, int ttl) {
        if (ttl <= 0) {
            throw new CommandException("Time to live cannot be negative!");
        }
    }

    @Override
    public String get(String key) {
        return null;
    }

}
