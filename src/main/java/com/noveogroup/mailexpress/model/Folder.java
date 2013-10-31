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

    private String name;

    private String iconPath;

    private Long parentFolderId;

    @OneToMany(mappedBy = "folder", cascade = CascadeType.ALL)
    private List<Message> messages;

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

    public List<Folder> getSubFolders() {
        return subFolders;
    }

    public void setSubFolders(List<Folder> subFolders) {
        this.subFolders = subFolders;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
