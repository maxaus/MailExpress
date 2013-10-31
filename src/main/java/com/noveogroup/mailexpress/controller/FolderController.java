package com.noveogroup.mailexpress.controller;

import com.noveogroup.mailexpress.dto.FolderNode;
import com.noveogroup.mailexpress.model.Folder;
import com.noveogroup.mailexpress.service.FolderService;
import org.richfaces.component.UITree;
import org.richfaces.event.TreeSelectionChangeEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.swing.tree.TreeNode;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Maxim Baev
 */
@Component
@ManagedBean
@ViewScoped
public class FolderController implements Serializable {

    private static final long serialVersionUID = 8105007650641624790L;

    @Autowired
    private FolderService folderService;

    private TreeNode currentSelection = null;

    private List<TreeNode> rootNodes = new ArrayList<TreeNode>();

    public List<TreeNode> getRootNodes() {
        if (rootNodes.isEmpty()) {
            initTree();
        }
        return rootNodes;
    }

    private void initTree() {
        List<Folder> folders = folderService.findAll();
        addData(folders);
    }

    private void addData(List<Folder> folders) {
        for (Folder folder : folders) {
            if (folder.getParentFolderId() == null) {
                FolderNode folderNode = new FolderNode(folder);
                rootNodes.add(folderNode);
            }
        }
    }

    public void selectionChanged(TreeSelectionChangeEvent selectionChangeEvent) {
        // considering only single selection
        List<Object> selection = new ArrayList<>(selectionChangeEvent.getNewSelection());
        Object currentSelectionKey = selection.get(0);
        UITree tree = (UITree) selectionChangeEvent.getSource();

        Object storedKey = tree.getRowKey();
        tree.setRowKey(currentSelectionKey);
        currentSelection = (TreeNode) tree.getRowData();
        tree.setRowKey(storedKey);
    }

    public TreeNode getCurrentSelection() {
        return currentSelection;
    }

    public void setCurrentSelection(TreeNode currentSelection) {
        this.currentSelection = currentSelection;
    }

}
