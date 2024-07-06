package org.maxicache;

public interface MaxiCache {

    void set(String key, String value, int ttl);
    String get(String key);

}