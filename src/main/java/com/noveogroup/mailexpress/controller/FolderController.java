package com.noveogroup.mailexpress.controller;

import com.noveogroup.mailexpress.dto.FolderNode;
import com.noveogroup.mailexpress.model.Folder;
import com.noveogroup.mailexpress.service.FolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

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
}
