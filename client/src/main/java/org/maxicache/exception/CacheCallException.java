package org.maxicache.exception;

public class CacheCallException extends RuntimeException {

    public CacheCallException(String message) {
        super(message);
    }

    public CacheCallException(String message, Throwable error) {
        super(message, error);
    }

}
