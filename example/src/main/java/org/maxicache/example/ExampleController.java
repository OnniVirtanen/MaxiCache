package org.maxicache.example;

import org.maxicache.interfaces.CacheClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/example")
public class ExampleController {

    private final CacheClient cacheClient;

    public ExampleController(CacheClient cacheClient) {
        this.cacheClient = cacheClient;
    }

    @GetMapping
    public String getBrand() {
        return cacheClient.get("car");
    }

    @PostMapping
    public void saveBrand() {
        String key = "car";
        String value = "volvo";
        int ttlSeconds = 15;
        cacheClient.set(key, value, ttlSeconds);
    }

}
