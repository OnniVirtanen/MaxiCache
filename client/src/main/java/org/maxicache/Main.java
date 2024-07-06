package org.maxicache;

import org.maxicache.implementation.CacheClientImpl;
import org.maxicache.interfaces.CacheClient;

public class Main {
    public static void main(String[] args) {
        CacheClient cache = new CacheClientImpl("localhost", 6379);
        cache.set("car", "246", 100);
        System.out.println(cache.get("car"));
    }
}