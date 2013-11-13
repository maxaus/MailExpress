package com.noveogroup.mailexpress.dto;

import com.google.common.collect.Iterators;
import com.noveogroup.mailexpress.domain.Folder;

import javax.swing.tree.TreeNode;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * @author Maxim Baev
 */
public class FolderNode extends NamedNode implements TreeNode {

    private static final long serialVersionUID = -3644535276155461599L;
    private static final String IMG_LIBRARY = "img";
    private Long id;
    private String iconPath;
    private boolean system;
    private List<FolderNode> subFolders = new ArrayList<FolderNode>();

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

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getIconPath() {
        return iconPath;
    }

    public boolean isSystem() {
        return system;
    }

    public List<FolderNode> getSubFolders() {
        return subFolders;
    }

    @Override
    public TreeNode getChildAt(final int childIndex) {
        return subFolders.get(childIndex);
    }

    @Override
    public int getChildCount() {
        return subFolders.size();
    }

    @Override
    public TreeNode getParent() {
        return null;
    }

    @Override
    public int getIndex(final TreeNode node) {
        return subFolders.indexOf(node);
    }

    @Override
    public boolean getAllowsChildren() {
        return true;
    }

    @Override
    public boolean isLeaf() {
        return subFolders.isEmpty();
    }

    @Override
    public Enumeration children() {
        return Iterators.asEnumeration(subFolders.iterator());
    }
}
