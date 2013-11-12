package com.noveogroup.mailexpress.controller;

import com.noveogroup.mailexpress.dto.FolderNode;
import com.noveogroup.mailexpress.domain.Folder;
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

    private String selectedFolderName;

    private Long selectedFolderId;

    private List<TreeNode> rootNodes = new ArrayList<>();

    public List<TreeNode> getRootNodes() {
        if (rootNodes.isEmpty()) {
            initTree();
        }
        return rootNodes;
    }

    private void initTree() {
        final List<Folder> folders = folderService.findAll();
        addData(folders);
    }

    private void addData(final List<Folder> folders) {
        for (final Folder folder : folders) {
            if (folder.getParentFolderId() == null) {
                final FolderNode folderNode = new FolderNode(folder);
                rootNodes.add(folderNode);
            }
        }
    }

    public void selectionListener(final TreeSelectionChangeEvent selectionChangeEvent) {
        final List<Object> selection = new ArrayList<>(selectionChangeEvent.getNewSelection());
        final Object currentSelectionKey = selection.get(0);
        final UITree tree = (UITree) selectionChangeEvent.getSource();

        final Object storedKey = tree.getRowKey();
        tree.setRowKey(currentSelectionKey);
        final FolderNode folderNode = (FolderNode) tree.getRowData();
        this.selectedFolderName = folderNode.getName();
        this.selectedFolderId = folderNode.getId();
        tree.setRowKey(storedKey);
    }

    public String getSelectedFolderName() {
        return selectedFolderName;
    }

    public Long getSelectedFolderId() {
        return selectedFolderId;
    }
}
