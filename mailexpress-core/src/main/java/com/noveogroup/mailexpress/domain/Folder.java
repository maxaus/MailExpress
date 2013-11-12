package com.noveogroup.mailexpress.domain;

import javax.persistence.*;
import java.util.List;

/**
 * @author Maxim Baev
 */
@Entity
@Table(name = "folder")
public class Folder extends AbstractEntity {

    private static final long serialVersionUID = -3200281633589669860L;

    @Column(name = "folder_name")
    private String name;

    @Column(name = "icon_path")
    private String iconPath;

    @Column(name = "parent_folder_id")
    private Long parentFolderId;

    @Column(name = "system_folder", nullable = false)
    private boolean systemFolder;

    @OneToMany(mappedBy = "parentFolderId", cascade = CascadeType.ALL)
    private List<Folder> subFolders;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(final String iconPath) {
        this.iconPath = iconPath;
    }

    public Long getParentFolderId() {
        return parentFolderId;
    }

    public void setParentFolderId(final Long parentFolderId) {
        this.parentFolderId = parentFolderId;
    }

    public boolean isSystemFolder() {
        return systemFolder;
    }

    public void setSystemFolder(final boolean systemFolder) {
        this.systemFolder = systemFolder;
    }

    public List<Folder> getSubFolders() {
        return subFolders;
    }

    public void setSubFolders(final List<Folder> subFolders) {
        this.subFolders = subFolders;
    }
}
