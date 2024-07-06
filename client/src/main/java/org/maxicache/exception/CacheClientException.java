package org.maxicache.exception;

public class NodeException extends RuntimeException {

    public NodeException(String message) {
        super(message);
    }

    public NodeException(String message, Throwable error) {
        super(message, error);
    }

}
