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

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }
}
