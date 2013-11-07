package com.noveogroup.mailexpress.model;

import javax.persistence.*;
import java.util.List;

/**
 * @author Maxim Baev
 */
@Entity
@Table(name = "folder")
public class Folder extends AbstractEntity {

    private static final long serialVersionUID = -3200281633589669860L;

    @Column(name = "name")
    private String name;

    @Column(name = "iconPath")
    private String iconPath;

    @Column(name = "parentFolderId")
    private Long parentFolderId;

    @Column(name = "systemFolder", nullable = false)
    private boolean systemFolder;

    @OneToMany(mappedBy = "parentFolderId", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Folder> subFolders;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public Long getParentFolderId() {
        return parentFolderId;
    }

    public void setParentFolderId(Long parentFolderId) {
        this.parentFolderId = parentFolderId;
    }

    public boolean isSystemFolder() {
        return systemFolder;
    }

    public void setSystemFolder(boolean systemFolder) {
        this.systemFolder = systemFolder;
    }

    public List<Folder> getSubFolders() {
        return subFolders;
    }

    public void setSubFolders(List<Folder> subFolders) {
        this.subFolders = subFolders;
    }
}
