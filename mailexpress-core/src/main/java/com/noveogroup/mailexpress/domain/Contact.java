package com.noveogroup.mailexpress.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;

/**
 * Saved mail contact.
 *
 * @author Maxim Baev
 */
@Entity
@Table(name = "contact")
public class Contact extends AbstractEntity {
    private static final long serialVersionUID = 2988819834890437610L;

    /**
     * Email address.
     */
    @Column(name = "email", length = 120, nullable = false)
    private String email;

    /**
     * Contact type (sender, receiver, etc.).
     */
    @Enumerated
    @Column(name = "contact_type")
    private ContactType type;

    /**
     * Default constructor.
     */
    public Contact() {
    }

    /**
     * Constructor.
     *
     * @param email email
     * @param type  type
     */
    public Contact(final String email, final ContactType type) {
        this.email = email;
        this.type = type;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(final String email) {
        this.email = email;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public ContactType getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(final ContactType type) {
        this.type = type;
    }
}
