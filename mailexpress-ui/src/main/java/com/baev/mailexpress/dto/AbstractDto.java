package com.baev.mailexpress.dto;

import java.io.Serializable;

/**
 * Base DTO.
 *
 * @author Maxim Baev
 */
public abstract class AbstractDto implements Serializable {
    private static final long serialVersionUID = 457381632253247229L;

    /**
     * Entity ID.
     */
    private Long id;

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
}
