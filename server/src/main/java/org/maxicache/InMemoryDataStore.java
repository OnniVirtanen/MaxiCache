package org.maxicache;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryDataStore implements DataStore {

    private final ConcurrentHashMap<String, ValueWithTTL> dataMap = new ConcurrentHashMap<>();

    @Override
    public void set(String key, String value, int ttl) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.SECOND, ttl);
        Date expirationDate = cal.getTime();
        ValueWithTTL v = new ValueWithTTL(key, expirationDate);
        dataMap.put(key, v);
    }

    @Override
    public String get(String key) {
        ValueWithTTL value = dataMap.get(key);
        if (value == null) {
            return null;
        }
        if (value.getExpiration().before(new Date())) {
            dataMap.remove(key);
            return null;
        }
        return value.getValue();
    }

}
