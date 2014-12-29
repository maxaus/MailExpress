package com.baev.mailexpress.dto.form;

import com.baev.mailexpress.dto.AbstractDto;

/**
 * Base DTO for UI forms.
 *
 * @author Maxim Baev
 */
public abstract class AbstractFormData extends AbstractDto {

    private static final long serialVersionUID = -91571287830040513L;

    /**
     * Form title.
     */
    private String title;

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
