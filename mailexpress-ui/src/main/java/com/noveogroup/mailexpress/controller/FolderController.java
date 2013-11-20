package com.noveogroup.mailexpress.controller;

import com.noveogroup.mailexpress.dto.FolderNode;
import com.noveogroup.mailexpress.domain.Folder;
import com.noveogroup.mailexpress.dto.form.FolderFormData;
import com.noveogroup.mailexpress.service.FolderService;
import org.richfaces.component.UITree;
import org.richfaces.event.TreeSelectionChangeEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.swing.tree.TreeNode;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * UI controller for folder manage actions.
 *
 * @author Maxim Baev
 */
@Component
@ManagedBean
@ViewScoped
public class FolderController implements Serializable {

    private static final long serialVersionUID = 8105007650641624790L;

    private static final Logger LOGGER = LoggerFactory.getLogger(FolderController.class);
    private static final String BUNDLE_NAME = "MailExpress";

    private String selectedFolderName;
    private Long selectedFolderId;
    private List<TreeNode> rootNodes = new ArrayList<>();
    private FolderFormData folderFormData;
    private String actionName;

    @Autowired
    private FolderService folderService;

    /**
     * Return list of tree nodes to build folder tree.
     *
     * @return List of tree nodes
     */
    public List<TreeNode> getRootNodes() {
        //TODO: re-init only when updated
//        if (rootNodes.isEmpty()) {
        initTree();
//        }
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

    public void openForm() {
        Folder item = null;
        if (selectedFolderName != null && !"create".equals(actionName)) {
            folderFormData.clear();
            item = folderService.findByName(selectedFolderName);
        }

        switch (actionName) {
            case "create":
                LOGGER.debug("Open form to create new folder.");
                folderFormData.setTitle(ResourceBundle.getBundle(BUNDLE_NAME,
                        FacesContext.getCurrentInstance().getViewRoot().getLocale()).getString("new_folder"));

                break;
            case "rename":
                LOGGER.debug("Rename folder. Name={}", selectedFolderName);
                folderFormData.setTitle(ResourceBundle.getBundle(BUNDLE_NAME,
                        FacesContext.getCurrentInstance().getViewRoot().getLocale()).getString("rename"));
                if (item != null) {
                    folderFormData.setName(item.getName());
                }
                break;
        }
    }

    /**
     * Remove void.
     */
    public void remove() {
        LOGGER.debug("Removing message. ID = {}", selectedFolderId);
        folderService.delete(selectedFolderId);
    }

    /**
     * Initializes folder tree.
     */
    private void initTree() {
        rootNodes.clear();
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

    /**
     * Gets folder form data.
     *
     * @return the folder form data
     */
    public FolderFormData getFolderFormData() {
        return folderFormData;
    }

    /**
     * Sets folder form data.
     *
     * @param folderFormData the folder form data
     */
    public void setFolderFormData(final FolderFormData folderFormData) {
        this.folderFormData = folderFormData;
    }

    /**
     * Gets action name.
     *
     * @return the action name
     */
    public String getActionName() {
        return actionName;
    }

    /**
     * Sets action name.
     *
     * @param actionName the action name
     */
    public void setActionName(final String actionName) {
        this.actionName = actionName;
    }
}
