package com.noveogroup.mailexpress.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Maxim Baev
 */
@Entity
@Table(name = "message")
public class Message extends AbstractEntity {

    private static final long serialVersionUID = -7069725440478759832L;

    @Column(name = "subject", length = 100)
    private String subject;

    @Lob
    @Column(name = "body")
    private String body;

    @Column(name = "unread")
    private boolean unread;

    @Column(name = "sent_date")
    private Date date;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sender_id")
    private Contact sender;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Contact> receivers = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "folder_id", nullable = false)
    private Folder folder;

    @OneToMany(mappedBy = "message", cascade = CascadeType.ALL)
    private List<Attachment> attachments = new ArrayList<>();

    public String getSubject() {
        return subject;
    }

    public void setSubject(final String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(final String body) {
        this.body = body;
    }

    public boolean isUnread() {
        return unread;
    }

    public void setUnread(final boolean unread) {
        this.unread = unread;
    }

    public Date getDate() {
        return date != null ? new Date(date.getTime()) : null;
    }

    public void setDate(final Date date) {
        if (date != null) {
            this.date = new Date(date.getTime());
        }
    }

    public Contact getSender() {
        return sender;
    }

    public void setSender(final Contact sender) {
        this.sender = sender;
    }

    public List<Contact> getReceivers() {
        return receivers;
    }

    public void setReceivers(final List<Contact> receivers) {
        this.receivers = receivers;
    }

    public Folder getFolder() {
        return folder;
    }

    public void setFolder(final Folder folder) {
        this.folder = folder;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(final List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public void addAttachment(final Attachment attachment) {
        attachment.setMessage(this);
        this.attachments.add(attachment);
    }

    public void addReceiver(final Contact receiver) {
        this.receivers.add(receiver);
    }
}
