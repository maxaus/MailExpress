package com.noveogroup.mailexpress.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * @author Maxim Baev
 */
@MappedSuperclass
public abstract class AbstractEntity implements Serializable {

    private static final long serialVersionUID = 8821325356867018706L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }
}
