package com.baev.mailexpress.dto.form;

/**
 * Folder form DTO.
 *
 * @author Maxim Baev
 */
public class FolderFormData extends AbstractFormData {

    private static final long serialVersionUID = 5154775171912224663L;

    /**
     * Folder name.
     */
    private String name;

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
}
