package org.maxicache.implementation;

import java.io.Serializable;

public class GetCacheCall implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String key;

    public GetCacheCall(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

}