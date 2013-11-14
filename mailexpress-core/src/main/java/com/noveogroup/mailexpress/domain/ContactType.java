package com.noveogroup.mailexpress.domain;

/**
 * Email contact type.
 *
 * @author Maxim Baev
 */
public enum ContactType {

    /**
     * Message sender.
     */
    SENDER("Sender"),

    /**
     * Message receiver.
     */
    RECEIVER("Receiver"),

    /**
     * Message copy receiver.
     */
    RECEIVER_COPY("Receiver copy");

    /**
     * Enum value.
     */
    private final String value;

    /**
     * Private constructor.
     *
     * @param value enum value
     */
    private ContactType(final String value) {
        this.value = value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return this.value;
    }
}
