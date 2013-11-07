package com.noveogroup.mailexpress.model;

import javax.persistence.*;
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
    
    @Column(name = "date")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private Contact sender;

    @OneToMany
    private List<Contact> receivers;

    @ManyToOne
    @JoinColumn(name = "folder_id", nullable = false)
    private Folder folder;

    @OneToMany(mappedBy = "message", cascade = CascadeType.ALL)
    private List<Attachment> attachments;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public boolean isUnread() {
        return unread;
    }

    public void setUnread(boolean unread) {
        this.unread = unread;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Contact getSender() {
        return sender;
    }

    public void setSender(Contact sender) {
        this.sender = sender;
    }

    public List<Contact> getReceivers() {
        return receivers;
    }

    public void setReceivers(List<Contact> receivers) {
        this.receivers = receivers;
    }

    public Folder getFolder() {
        return folder;
    }

    public void setFolder(Folder folder) {
        this.folder = folder;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }
}
