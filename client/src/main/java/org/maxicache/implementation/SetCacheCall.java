package org.maxicache.implementation;

import java.io.Serializable;

public class SetCacheCall implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String key;
    private final String value;
    private final int ttl;

    public SetCacheCall(String key, String value, int ttl) {
        this.key = key;
        this.value = value;
        this.ttl = ttl;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public int getTtl() {
        return ttl;
    }

}
