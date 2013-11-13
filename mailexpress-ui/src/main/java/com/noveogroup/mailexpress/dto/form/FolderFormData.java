package com.noveogroup.mailexpress.dto.form;

import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.io.Serializable;

/**
 * @author Maxim Baev
 */
@Component
@ManagedBean
@RequestScoped
public class FolderFormData implements Serializable {

    private static final long serialVersionUID = 5154775171912224663L;

    private Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }
}
