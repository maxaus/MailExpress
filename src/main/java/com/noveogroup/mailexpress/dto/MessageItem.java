package com.noveogroup.mailexpress.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Maxim Baev
 */
public class MessageItem implements Serializable {

    private static final long serialVersionUID = 3165843357002271177L;

    private Long id;

    private String subject;

    private String sender;

    private String date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
