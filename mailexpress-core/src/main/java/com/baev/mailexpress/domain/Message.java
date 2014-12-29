package com.baev.mailexpress.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Message.
 *
 * @author Maxim Baev
 */
@Entity
@Table(name = "message")
public class Message extends AbstractEntity {

    private static final long serialVersionUID = -7069725440478759832L;

    /**
     * Message subject.
     */
    @Column(name = "subject", length = 100)
    private String subject;

    /**
     * Message body.
     */
    @Lob
    @Column(name = "body")
    private String body;

    /**
     * Flag indicates if message is still unread.
     */
    @Column(name = "unread")
    private boolean unread;

    /**
     * Date when message was sent.
     */
    @Column(name = "sent_date")
    private Date date;

    /**
     * Sender of the message.
     */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sender_id")
    private Contact sender;

    /**
     * List of all message receivers.
     */
    @OneToMany(cascade = CascadeType.ALL)
    private List<Contact> receivers = new ArrayList<>();

    /**
     * Message folder.
     */
    @ManyToOne
    @JoinColumn(name = "folder_id", nullable = false)
    private Folder folder;

    /**
     * List of message attachments.
     */
    @OneToMany(mappedBy = "message", cascade = CascadeType.ALL)
    private List<Attachment> attachments = new ArrayList<>();

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
     * Gets date.
     *
     * @return the date
     */
    public Date getDate() {
        return date != null ? new Date(date.getTime()) : null;
    }

    /**
     * Sets date.
     *
     * @param date the date
     */
    public void setDate(final Date date) {
        if (date != null) {
            this.date = new Date(date.getTime());
        }
    }

    /**
     * Gets sender.
     *
     * @return the sender
     */
    public Contact getSender() {
        return sender;
    }

    /**
     * Sets sender.
     *
     * @param sender the sender
     */
    public void setSender(final Contact sender) {
        this.sender = sender;
    }

    /**
     * Gets receivers.
     *
     * @return the receivers
     */
    public List<Contact> getReceivers() {
        return receivers;
    }

    /**
     * Sets receivers.
     *
     * @param receivers the receivers
     */
    public void setReceivers(final List<Contact> receivers) {
        this.receivers = receivers;
    }

    /**
     * Gets folder.
     *
     * @return the folder
     */
    public Folder getFolder() {
        return folder;
    }

    /**
     * Sets folder.
     *
     * @param folder the folder
     */
    public void setFolder(final Folder folder) {
        this.folder = folder;
    }

    /**
     * Gets attachments.
     *
     * @return the attachments
     */
    public List<Attachment> getAttachments() {
        return attachments;
    }

    /**
     * Sets attachments.
     *
     * @param attachments the attachments
     */
    public void setAttachments(final List<Attachment> attachments) {
        this.attachments = attachments;
    }

    /**
     * Add attachment.
     *
     * @param attachment the attachment
     */
    public void addAttachment(final Attachment attachment) {
        attachment.setMessage(this);
        this.attachments.add(attachment);
    }

    /**
     * Add receiver.
     *
     * @param receiver the receiver
     */
    public void addReceiver(final Contact receiver) {
        this.receivers.add(receiver);
    }
}
