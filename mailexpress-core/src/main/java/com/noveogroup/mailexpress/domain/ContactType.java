package com.noveogroup.mailexpress.domain;

/**
 * @author Maxim Baev
 */
public enum ContactType {

    SENDER("Sender"),

    RECEIVER("Receiver"),

    RECEIVER_COPY("Receiver copy");

    private final String value;

    ContactType(final String value) {
        this.value = value;
    }


    @Override
    public String toString() {
        return this.value;
    }
}
