package org.maxicache.shared;

import java.io.Serializable;

public abstract class CacheCall implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String name;

    public CacheCall(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}