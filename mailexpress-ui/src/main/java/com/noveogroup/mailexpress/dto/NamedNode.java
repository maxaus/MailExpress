package com.noveogroup.mailexpress.dto;

import java.io.Serializable;

/**
 * @author Maxim Baev
 */
public class NamedNode implements Serializable {

    private static final long serialVersionUID = 7320356885789207660L;

    protected String type;
    protected String name;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
