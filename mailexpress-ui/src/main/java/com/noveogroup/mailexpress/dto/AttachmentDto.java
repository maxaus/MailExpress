package com.noveogroup.mailexpress.dto;

import java.io.Serializable;

/**
 * Attachment DTO.
 *
 * @author Maxim Baev
 */
public class AttachmentDto implements Serializable {
    private static final long serialVersionUID = 7576647276227419894L;

    private Long id;
    private String path;

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
}
