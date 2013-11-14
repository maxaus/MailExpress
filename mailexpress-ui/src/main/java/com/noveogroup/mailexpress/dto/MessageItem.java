package com.noveogroup.mailexpress.dto;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * DTO represents message entity used in message list.
 * @author Maxim Baev
 */
public class MessageItem implements Serializable {

    private static final long serialVersionUID = 3165843357002271177L;

    private Long id;
    private boolean unread;
    private String subject;
    private String sender;
    private String date;
    private String body;

    /**
     * Gets id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Is unread.
     *
     * @return the boolean
     */
    public boolean isUnread() {
        return unread;
    }

    /**
     * Sets unread.
     *
     * @param unread the unread
     */
    public void setUnread(final boolean unread) {
        this.unread = unread;
    }

    /**
     * Gets subject.
     *
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Sets subject.
     *
     * @param subject the subject
     */
    public void setSubject(final String subject) {
        this.subject = subject;
    }

    /**
     * Gets sender.
     *
     * @return the sender
     */
    public String getSender() {
        return sender;
    }

    /**
     * Sets sender.
     *
     * @param sender the sender
     */
    public void setSender(final String sender) {
        this.sender = sender;
    }

    /**
     * Gets date.
     *
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets date.
     *
     * @param date the date
     */
    public void setDate(final String date) {
        this.date = date;
    }

    /**
     * Gets body.
     *
     * @return the body
     */
    public String getBody() {
        return body;
    }

    /**
     * Sets body.
     *
     * @param body the body
     */
    public void setBody(final String body) {
        this.body = body;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
