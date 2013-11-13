package com.noveogroup.mailexpress.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Maxim Baev
 */
@Entity
@Table(name = "contact")
public class Contact extends AbstractEntity {
    private static final long serialVersionUID = 2988819834890437610L;

    @Column(name = "email", length = 120, nullable = false)
    private String email;

    public Contact() {}

    public Contact(final String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }
}
