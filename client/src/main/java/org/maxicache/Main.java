package org.maxicache;

public class Main {
    public static void main(String[] args) {
        MaxiNodeImpl node = new MaxiNodeImpl("localhost", 6379);

        MaxiResource resource = node.getResource();

        resource.set("car", "246", 100);
        System.out.println(resource.get("car"));
    }
}