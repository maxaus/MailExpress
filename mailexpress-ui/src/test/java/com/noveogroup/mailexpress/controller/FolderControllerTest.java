package com.noveogroup.mailexpress.controller;

import com.noveogroup.mailexpress.domain.Folder;
import org.junit.Test;
import org.mockito.InjectMocks;

import javax.swing.tree.TreeNode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * Tests {@link FolderController} instance.
 *
 * @author Maxim Baev
 */
public class FolderControllerTest extends AbstractUIControllerTest {

    private static final Long TEST_FOLDER_ID = 1L;
    private static final String TEST_FOLDER_NAME = "Inbox";
    @InjectMocks
    private FolderController folderController;

    @Test
    public void testGetRootNodes() {
        final Folder folder = new Folder();
        folder.setSubFolders(new ArrayList<Folder>());
        folder.setName(TEST_FOLDER_NAME);
        final List<Folder> folders = Collections.singletonList(folder);
        when(folderService.findAll()).thenReturn(folders);
        final List<TreeNode> rootNodes = folderController.getRootNodes();

        verify(folderService).findAll();
        assertNotNull(rootNodes);
        assertEquals(1, rootNodes.size());
        assertNotNull(folderController.getFolderNames());
        assertEquals(1, folderController.getFolderNames().size());
        assertEquals(TEST_FOLDER_NAME, folderController.getFolderNames().get(0));
    }

    @Test
    public void testOpenForm() {
//        folderController.setActionName("rename");
//        folderController.setSelectedFolderName("Inbox");
//        Folder folder = new Folder();
//        when(folderService.findByName("Inbox")).thenReturn(folder);
//        folderController.openForm();
//        assertEquals(folder.getId(), folderController.getFolderFormData().getId());
    }

    @Test
    public void testSaveFolder() {
        folderController.getFolderFormData().setId(0L);
        folderController.getFolderFormData().setName(TEST_FOLDER_NAME);
        when(folderService.save(any(Folder.class))).thenReturn(new Folder());
        folderController.saveFolder();
        verify(folderService).save(any(Folder.class));
    }

    @Test
    public void testUpdateFolder() {
        folderController.setSelectedFolderName(TEST_FOLDER_NAME);
        folderController.getFolderFormData().setId(TEST_FOLDER_ID);
        folderController.getFolderFormData().setName(TEST_FOLDER_NAME);
        when(folderService.findByName(TEST_FOLDER_NAME)).thenReturn(new Folder());
        when(folderService.update(any(Folder.class))).thenReturn(new Folder());
        folderController.saveFolder();
        verify(folderService).findByName(TEST_FOLDER_NAME);
        verify(folderService).update(any(Folder.class));
    }

    @Test
    public void testRemoveFolder() {
        doNothing().when(folderService).delete(anyLong());
        folderController.removeFolder();
        verify(folderService).delete(anyLong());
    }
}
