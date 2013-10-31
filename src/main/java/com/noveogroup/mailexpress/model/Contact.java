package com.noveogroup.mailexpress.model;

import javax.persistence.*;

/**
 * @author Maxim Baev
 */
@Entity
@Table(name = "contact")
public class Contact extends AbstractEntity {
    private static final long serialVersionUID = 2988819834890437610L;

    @Column(name = "name", length = 120, nullable = false)
    private String name;

    @Column(name = "email", length = 120, nullable = false)
    private String email;

    @Column(name = "in_contact_list")
    private boolean inContactList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isInContactList() {
        return inContactList;
    }

    public void setInContactList(boolean inContactList) {
        this.inContactList = inContactList;
    }
}
