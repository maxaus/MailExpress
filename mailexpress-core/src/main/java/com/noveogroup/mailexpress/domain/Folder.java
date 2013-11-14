package com.noveogroup.mailexpress.domain;

import javax.persistence.*;
import java.util.List;

/**
 * Message folder.
 *
 * @author Maxim Baev
 */
@Entity
@Table(name = "folder")
public class Folder extends AbstractEntity {

    private static final long serialVersionUID = -3200281633589669860L;

    /**
     * Folder name.
     */
    @Column(name = "folder_name")
    private String name;

    /**
     * Folder icon path (for default system folders).
     */
    @Column(name = "icon_path")
    private String iconPath;

    /**
     * Parent folder ID for subfolders.
     */
    @Column(name = "parent_folder_id")
    private Long parentFolderId;

    /**
     * Indicates if folder if default system folder (like "Inbox", "Sent", etc.).
     */
    @Column(name = "system_folder", nullable = false)
    private boolean systemFolder;

    /**
     * List of subfolders.
     */
    @OneToMany(mappedBy = "parentFolderId", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Folder> subFolders;

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
     * Gets icon path.
     *
     * @return the icon path
     */
    public String getIconPath() {
        return iconPath;
    }

    /**
     * Sets icon path.
     *
     * @param iconPath the icon path
     */
    public void setIconPath(final String iconPath) {
        this.iconPath = iconPath;
    }

    /**
     * Gets parent folder id.
     *
     * @return the parent folder id
     */
    public Long getParentFolderId() {
        return parentFolderId;
    }

    /**
     * Sets parent folder id.
     *
     * @param parentFolderId the parent folder id
     */
    public void setParentFolderId(final Long parentFolderId) {
        this.parentFolderId = parentFolderId;
    }

    /**
     * Is system folder.
     *
     * @return the boolean
     */
    public boolean isSystemFolder() {
        return systemFolder;
    }

    /**
     * Sets system folder.
     *
     * @param systemFolder the system folder
     */
    public void setSystemFolder(final boolean systemFolder) {
        this.systemFolder = systemFolder;
    }

    /**
     * Gets sub folders.
     *
     * @return the sub folders
     */
    public List<Folder> getSubFolders() {
        return subFolders;
    }

    /**
     * Sets sub folders.
     *
     * @param subFolders the sub folders
     */
    public void setSubFolders(final List<Folder> subFolders) {
        this.subFolders = subFolders;
    }
}
