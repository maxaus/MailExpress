package com.noveogroup.mailexpress.controller;

import com.noveogroup.mailexpress.domain.Folder;
import com.noveogroup.mailexpress.dto.FolderNode;
import com.noveogroup.mailexpress.dto.MessageDto;
import com.noveogroup.mailexpress.dto.form.FolderFormData;
import org.richfaces.component.UITree;
import org.richfaces.event.TreeSelectionChangeEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.swing.tree.TreeNode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * UI controller for folder manage actions.
 *
 * @author Maxim Baev
 */
@Component
@ManagedBean
@ViewScoped
public class FolderController extends AbstractUIController {

    private static final long serialVersionUID = 8105007650641624790L;

    private static final Logger LOGGER = LoggerFactory.getLogger(FolderController.class);
    private static final String CREATE_FOLDER_ACTION = "new_folder";

    private String actionName;
    private String selectedFolderName;
    private Long selectedFolderId;
    private boolean selectedFolderSystem;
    private FolderFormData folderFormData = new FolderFormData();
    private List<TreeNode> rootNodes = new ArrayList<>();
    private Set<String> folderNames = new HashSet<>();

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
     * Gets folder names.
     *
     * @return the folder names
     */
    public List<String> getFolderNames() {
        return new ArrayList<>(folderNames);
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
        this.selectedFolderSystem = folderNode.isSystem();
        tree.setRowKey(storedKey);
        messageListData.setSelectedIndexes(new ArrayList<Long>());
        messageListData.setSelectedItems(new ArrayList<MessageDto>());
    }

    /**
     * Saves or updates folder.
     */
    public void saveFolder() {
        if (folderFormData.getId() != 0) {
            final Folder folder = folderService.findByName(selectedFolderName);
            folder.setName(folderFormData.getName());
            folderService.update(folder);
        } else {
            final Folder folder = new Folder();
            folder.setName(folderFormData.getName());
            folder.setParentFolderId(selectedFolderId);
            folderService.save(folder);
        }
        rootNodes.clear();
        folderNames.clear();
    }

    /**
     * Prepares appropriate data for folder form being opened.
     */
    public void openForm() {
        LOGGER.debug("Open folder form. Action: {}", actionName);
        folderFormData = new FolderFormData();
        if (selectedFolderName != null && !CREATE_FOLDER_ACTION.equals(actionName)) {
            final Folder folder = folderService.findByName(selectedFolderName);
            if (folder != null) {
                folderFormData.setId(folder.getId());
                folderFormData.setName(folder.getName());
            }
        }

        folderFormData.setTitle(ResourceBundle.getBundle(BUNDLE_NAME,
                FacesContext.getCurrentInstance().getViewRoot().getLocale()).getString(actionName));
    }

    /**
     * Removes folder.
     */
    public void removeFolder() {
        LOGGER.debug("Removing folder. ID = {}", selectedFolderId);
        folderService.delete(selectedFolderId);
        rootNodes.clear();
        folderNames.clear();
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
     * Sets action name.
     *
     * @param actionName the action name
     */
    public void setActionName(final String actionName) {
        this.actionName = actionName;
    }

    /**
     * Sets selected folder name.
     *
     * @param selectedFolderName the selected folder name
     */
    public void setSelectedFolderName(final String selectedFolderName) {
        this.selectedFolderName = selectedFolderName;
    }

    /**
     * Is selected folder is system folder.
     *
     * @return true for system folder
     */
    public boolean isSelectedFolderSystem() {
        return selectedFolderSystem;
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
            folderNames.add(folder.getName());
        }
    }

}
