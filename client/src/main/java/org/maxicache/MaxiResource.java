package org.maxicache;

public class MaxiResource implements MaxiCache {

    @Override
    public void set(String key, String value, int ttl) {
        if (ttl <= 0) {
            throw new MaxiResourceException("Time to live cannot be negative!");
        }
    }

    @Override
    public String get(String key) {
        return null;
    }

}
