package com.noveogroup.mailexpress.model;

import javax.persistence.*;

/**
 * @author Maxim Baev
 */
@Entity
@Table(name = "attachment")
public class Attachment extends AbstractEntity {

    private static final long serialVersionUID = -6996234140992810706L;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "path", length = 255)
    private String path;

    @ManyToOne
    @JoinColumn(name = "message_id", nullable = false)
    private Message message;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
