package com.noveogroup.mailexpress.dto;

/**
 * Folder DTO.
 *
 * @author Maxim Baev
 */
public class FolderDto extends AbstractDto {

    private static final long serialVersionUID = 9197188787470051705L;

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
