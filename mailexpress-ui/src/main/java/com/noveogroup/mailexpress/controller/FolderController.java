package com.noveogroup.mailexpress.controller;

import com.noveogroup.mailexpress.dto.FolderNode;
import com.noveogroup.mailexpress.domain.Folder;
import com.noveogroup.mailexpress.dto.form.FolderFormData;
import com.noveogroup.mailexpress.service.FolderService;
import org.richfaces.component.UITree;
import org.richfaces.event.TreeSelectionChangeEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.swing.tree.TreeNode;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * UI controller for folder manage actions.
 *
 * @author Maxim Baev
 */
@Component
@ManagedBean
@SessionScoped
public class FolderController implements Serializable {

    private static final long serialVersionUID = 8105007650641624790L;

    private String selectedFolderName;
    private Long selectedFolderId;
    private List<TreeNode> rootNodes = new ArrayList<>();

    @Autowired
    private FolderService folderService;

    @Autowired
    private FolderFormData folderFormData;

    /**
     * Return list of tree nodes to build folder tree.
     *
     * @return List of tree nodes
     */
    public List<TreeNode> getRootNodes() {
        if (rootNodes.isEmpty()) {
            initTree();
        }
        return rootNodes;
    }

    /**
     * Listens to folder tree node selection event.
     *
     * @param selectionChangeEvent event
     */
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

    /**
     * Returns currently selected folder name.
     *
     * @return Folder name
     */
    public String getSelectedFolderName() {
        return selectedFolderName;
    }

    /**
     * Returns currently selected folder ID.
     *
     * @return Folder ID
     */
    public Long getSelectedFolderId() {
        return selectedFolderId;
    }

    /**
     * Saves new folder.
     */
    public void saveFolder() {
        final Folder folder = new Folder();
        folder.setName(folderFormData.getName());
        folder.setParentFolderId(selectedFolderId);
        folderService.save(folder);
    }

    public void remove() {

    }

    /**
     * Initializes folder tree.
     */
    private void initTree() {
        final List<Folder> folders = folderService.findAll();
        addData(folders);
    }

    /**
     * Adds folders to the tree.
     *
     * @param folders all folders
     */
    private void addData(final List<Folder> folders) {
        for (final Folder folder : folders) {
            if (folder.getParentFolderId() == null) {
                final FolderNode folderNode = new FolderNode(folder);
                rootNodes.add(folderNode);
            }
        }
    }
}
