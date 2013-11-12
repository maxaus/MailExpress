package com.noveogroup.mailexpress.domain;

import javax.persistence.*;

/**
 * @author Maxim Baev
 */
@Entity
@Table(name = "attachment")
public class Attachment extends AbstractEntity {

    private static final long serialVersionUID = -6996234140992810706L;

    @Column(name = "path", length = 255, nullable = false)
    private String path;

    @ManyToOne
    @JoinColumn(name = "message_id", nullable = false)
    private Message message;

    public String getPath() {
        return path;
    }

    public void setPath(final String path) {
        this.path = path;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(final Message message) {
        this.message = message;
    }
}
