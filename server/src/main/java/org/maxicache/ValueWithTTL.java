package org.maxicache;

import java.util.Date;

public class ValueWithTTL {

    private final String value;
    private final Date expiration;

    public ValueWithTTL(String value, Date expiration) {
        this.value = value;
        this.expiration = expiration;
    }

    public String getValue() {
        return value;
    }

    public Date getExpiration() {
        return expiration;
    }

}
