package org.maxicache;

import org.maxicache.implementation.Node;
import org.maxicache.interfaces.MaxiCache;

public class Main {
    public static void main(String[] args) {
        MaxiCache cache = new Node("localhost", 6379);
        cache.set("car", "246", 100);
        System.out.println(cache.get("car"));
    }
}