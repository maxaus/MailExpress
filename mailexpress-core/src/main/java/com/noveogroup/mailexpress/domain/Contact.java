package com.noveogroup.mailexpress.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
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

    @Enumerated
    @Column(name = "contact_type")
    private ContactType type;

    public Contact() {}

    public Contact(final String email, final ContactType type) {
        this.email = email;
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public ContactType getType() {
        return type;
    }

    public void setType(final ContactType type) {
        this.type = type;
    }
}
