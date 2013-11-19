package com.noveogroup.mailexpress.dto.form;

import java.io.Serializable;

/**
 * Base DTO for UI forms.
 * @author Maxim Baev
 */
public abstract class AbstractFormData implements Serializable {
    private static final long serialVersionUID = -91571287830040513L;

    /**
     * Entity ID.
     */
    private Long id;

    /**
     * Form title.
     */
    private String title;

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
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets title.
     *
     * @param title the title
     */
    public void setTitle(final String title) {
        this.title = title;
    }
}
