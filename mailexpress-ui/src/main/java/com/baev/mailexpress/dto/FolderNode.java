package com.baev.mailexpress.dto;

import com.google.common.collect.Iterators;
import com.baev.mailexpress.domain.Folder;

import javax.swing.tree.TreeNode;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * DTO representing folder tree node.
 *
 * @author Maxim Baev
 */
public class FolderNode extends NamedNode implements TreeNode {

    private static final long serialVersionUID = -3644535276155461599L;
    private static final String IMG_LIBRARY = "img";

    private Long id;
    private String iconPath;
    private boolean system;
    private boolean expanded = true;
    private List<FolderNode> subFolders = new ArrayList<FolderNode>();

    /**
     * Constructor.
     *
     * @param folder folder entity
     */
    public FolderNode(final Folder folder) {
        this.setType("folder");
        if (folder != null) {
            this.id = folder.getId();
            this.name = folder.getName();
            this.iconPath = IMG_LIBRARY + ":" + folder.getIconPath();
            this.system = folder.isSystemFolder();
            if (!folder.getSubFolders().isEmpty()) {
                for (final Folder subFolder : folder.getSubFolders()) {
                    final FolderNode subFolderNode = new FolderNode(subFolder);
                    this.subFolders.add(subFolderNode);
                }
            }
        }
    }

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
     * Gets icon path.
     *
     * @return the icon path
     */
    public String getIconPath() {
        return iconPath;
    }

    /**
     * Is system.
     *
     * @return the boolean
     */
    public boolean isSystem() {
        return system;
    }

    /**
     * Gets sub folders.
     *
     * @return the sub folders
     */
    public List<FolderNode> getSubFolders() {
        return subFolders;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TreeNode getChildAt(final int childIndex) {
        return subFolders.get(childIndex);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getChildCount() {
        return subFolders.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TreeNode getParent() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getIndex(final TreeNode node) {
        return subFolders.indexOf(node);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getAllowsChildren() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isLeaf() {
        return subFolders.isEmpty();
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(final boolean expanded) {
        this.expanded = expanded;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Enumeration children() {
        return Iterators.asEnumeration(subFolders.iterator());
    }
}
