package org.maxicache;

public interface DataStore {

    void set(String key, String value, int ttl);
    String get(String key);

}
