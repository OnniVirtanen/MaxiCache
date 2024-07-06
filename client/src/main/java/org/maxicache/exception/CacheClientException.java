package org.maxicache.exception;

public class CacheClientException extends RuntimeException {

    public CacheClientException(String message) {
        super(message);
    }

    public CacheClientException(String message, Throwable error) {
        super(message, error);
    }

}
