package com.noveogroup.mailexpress.dto.form;

import com.noveogroup.mailexpress.dto.AttachmentDto;
import com.noveogroup.mailexpress.util.data.Clearable;
import java.util.ArrayList;
import java.util.List;

/**
 * Message form DTO.
 *
 * @author Maxim Baev
 */
public class MessageFormData extends AbstractFormData implements Clearable {

    private static final long serialVersionUID = -2081260075515423870L;

    /**
     * Subject
     */
    private String subject;

    /**
     * Body.
     */
    private String body;

    /**
     * List of receivers email addresses.
     */
    private List<String> receivers;

    /**
     * List of copy receivers email addresses.
     */
    private List<String> copies;

    /**
     * List of uploaded files to be attached to the message.
     */
    private List<AttachmentDto> attachments = new ArrayList<>();

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
     * Gets receivers.
     *
     * @return the receivers
     */
    public List<String> getReceivers() {
        return receivers;
    }

    /**
     * Sets receivers.
     *
     * @param receivers the receivers
     */
    public void setReceivers(final List<String> receivers) {
        this.receivers = receivers;
    }

    /**
     * Gets copies.
     *
     * @return the copies
     */
    public List<String> getCopies() {
        return copies;
    }

    /**
     * Sets copies.
     *
     * @param copies the copies
     */
    public void setCopies(final List<String> copies) {
        this.copies = copies;
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
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        subject = null;
    }
}
