package com.noveogroup.mailexpress.dto;

import java.io.Serializable;

/**
 * Base class for UI tree nodes with name and type attributes.
 *
 * @author Maxim Baev
 */
public class NamedNode implements Serializable {

    private static final long serialVersionUID = 7320356885789207660L;

    /**
     * Node type.
     */
    protected String type;

    /**
     * Node name.
     */
    protected String name;

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(final String type) {
        this.type = type;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return this.name;
    }
}
