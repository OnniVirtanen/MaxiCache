package org.maxicache.shared;

public class SetCacheCall extends CacheCall {

    private final String key;
    private final String value;
    private final int ttl;

    public SetCacheCall(String key, String value, int ttl) {
        super("set");
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
