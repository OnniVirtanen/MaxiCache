package org.maxicache.shared;

public class GetCacheCall extends CacheCall {

    private final String key;

    public GetCacheCall(String key) {
        super("get");
        this.key = key;
    }

    public String getKey() {
        return key;
    }

}