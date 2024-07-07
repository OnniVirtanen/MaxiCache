package org.maxicache.interfaces;

public interface CacheClient {

    void set(String key, String value, int ttl);
    String get(String key);
    void closeConnection();

}