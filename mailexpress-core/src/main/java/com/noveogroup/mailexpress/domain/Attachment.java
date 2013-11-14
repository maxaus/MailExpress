package com.noveogroup.mailexpress.domain;

import javax.persistence.*;

/**
 * Message attachment.
 *
 * @author Maxim Baev
 */
@Entity
@Table(name = "attachment")
public class Attachment extends AbstractEntity {

    private static final long serialVersionUID = -6996234140992810706L;

    /**
     * File path to the attachment in server folder.
     */
    @Column(name = "path", length = 255, nullable = false)
    private String path;

    /**
     * Message that file is attached to.
     */
    @ManyToOne
    @JoinColumn(name = "message_id", nullable = false)
    private Message message;

    /**
     * Gets path.
     *
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * Sets path.
     *
     * @param path the path
     */
    public void setPath(final String path) {
        this.path = path;
    }

    /**
     * Gets message.
     *
     * @return the message
     */
    public Message getMessage() {
        return message;
    }

    /**
     * Sets message.
     *
     * @param message the message
     */
    public void setMessage(final Message message) {
        this.message = message;
    }
}
