package com.baev.mailexpress.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * Base class for all domain entities.
 *
 * @author Maxim Baev
 */
@MappedSuperclass
public abstract class AbstractEntity implements Serializable {

    private static final long serialVersionUID = 8821325356867018706L;

    /**
     * Entity ID.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
