package com.noveogroup.mailexpress.dto;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Message DTO.
 *
 * @author Maxim Baev
 */
public class MessageDto implements Serializable {

    private static final long serialVersionUID = 3165843357002271177L;

    private Long id;
    private String subject;
    private String sender;
    private String date;
    private String body;
    private boolean unread;
    private List<AttachmentDto> attachments = new ArrayList<>();
    private List<String> items = Arrays.asList("a", "b", "c", "d", "e", "f");

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
     * Gets attachments.
     *
     * @return the attachments
     */
    public List<AttachmentDto> getAttachments() {
        return attachments;
    }

    /**
     * Sets attachments.
     *
     * @param attachments the attachments
     */
    public void setAttachments(final List<AttachmentDto> attachments) {
        this.attachments = attachments;
    }

    /**
     * Add attachment.
     *
     * @param attachment the attachment
     */
    public void addAttachment(final AttachmentDto attachment) {
        this.attachments.add(attachment);
    }

    /**
     * Is message with attachment.
     *
     * @return the boolean
     */
    public boolean isWithAttachment() {
        return CollectionUtils.isNotEmpty(this.attachments);
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

    public List<String> getItems() {
        return items;
    }

    public void setItems(final List<String> items) {
        this.items = items;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
