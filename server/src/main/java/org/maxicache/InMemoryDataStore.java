package org.maxicache;

import java.util.concurrent.ConcurrentHashMap;

public class InMemoryDataStore implements DataStore {

    private final ConcurrentHashMap<String, String> dataMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Integer> ttlMap = new ConcurrentHashMap<>();

    @Override
    public void set(String key, String value, int ttl) {
        dataMap.put(key, value);
        ttlMap.put(key, ttl);
    }

    @Override
    public String get(String key) {
        return dataMap.get(key);
    }

}
