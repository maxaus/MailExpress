package com.noveogroup.mailexpress.dto.form;

import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.io.Serializable;

/**
 * Folder form DTO.
 *
 * @author Maxim Baev
 */
@Component
@ManagedBean
@RequestScoped
public class FolderFormData implements Serializable {

    private static final long serialVersionUID = 5154775171912224663L;

    /**
     * Folder ID.
     */
    private Long id;

    /**
     * Folder name.
     */
    private String name;

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
