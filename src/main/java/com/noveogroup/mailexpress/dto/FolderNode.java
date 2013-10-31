package com.noveogroup.mailexpress.dto;

import com.google.common.collect.Iterators;
import com.noveogroup.mailexpress.model.Folder;

import javax.swing.tree.TreeNode;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * @author Maxim Baev
 */
public class FolderNode extends NamedNode implements TreeNode {

    private static final long serialVersionUID = -3644535276155461599L;
    private String iconPath;
    private List<FolderNode> subFolders = new ArrayList<FolderNode>();

    public FolderNode(Folder folder) {
        this.setType("folder");
        if (folder != null) {
            this.name = folder.getName();
            this.iconPath = folder.getIconPath();
            if (!folder.getSubFolders().isEmpty()) {
                for (Folder subFolder : folder.getSubFolders()) {
                    FolderNode subFolderNode = new FolderNode(subFolder);
                    this.subFolders.add(subFolderNode);
                }
            }
        }
    }

    public String getIconPath() {
        return iconPath;
    }

    public List<FolderNode> getSubFolders() {
        return subFolders;
    }

    @Override
    public TreeNode getChildAt(int childIndex) {
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
    public int getIndex(TreeNode node) {
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
