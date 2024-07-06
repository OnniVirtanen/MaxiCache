package org.maxicache;

import java.util.regex.Pattern;

public class MaxiNodeImpl implements MaxiNode {

    private final String ip;
    private final int port;
    final String PORT_PATTERN = "^((6553[0-5])|(655[0-2][0-9])|(65[0-4][0-9]{2})|(6[0-4][0-9]{3})|([1-5][0-9]{4})|([0-5]{0,5})|([0-9]{1,4}))$";
    final String IPV4_PATTERN = "^(([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])(\\.(?!$)|$)){4}$";
    private final MaxiResource maxiResource;

    public MaxiNodeImpl(String ip, int port) {
        validate(ip, port);
        this.ip = ip;
        this.port = port;
        this.maxiResource = createResource();
    }

    private void validate(String ip, int port) {
        boolean isValidIp = Pattern.compile(IPV4_PATTERN).matcher(ip).matches();
        if (!isValidIp && !ip.equals("localhost")) {
            throw new MaxiNodeException("Ip is not in valid form!");
        }
        boolean isValidPort = Pattern.compile(PORT_PATTERN).matcher(String.valueOf(port)).matches();
        if (!isValidPort) {
            throw new MaxiNodeException("Port is not in valid form!");
        }
    }

    private MaxiResource createResource() {
        // TODO
        return new MaxiResource();
    }

    @Override
    public MaxiResource getResource() {
        return maxiResource;
    }

}
