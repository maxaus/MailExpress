package com.noveogroup.mailexpress.dto;

import java.io.Serializable;

/**
 * @author Maxim Baev
 */
public class MessageItem implements Serializable {

    private static final long serialVersionUID = 3165843357002271177L;

    private Long id;

    private boolean unread;

    private boolean withAttachment;

    private String subject;

    private String sender;

    private String date;

    private String body;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isUnread() {
        return unread;
    }

    public void setUnread(boolean unread) {
        this.unread = unread;
    }

    public boolean isWithAttachment() {
        return withAttachment;
    }

    public void setWithAttachment(boolean withAttachment) {
        this.withAttachment = withAttachment;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
