
# Example client code

CacheClient cache = new CacheClientImpl("localhost", 6379);
cache.set("car", "246", 100);
System.out.println(cache.get("car"));
cache.closeConnection();
