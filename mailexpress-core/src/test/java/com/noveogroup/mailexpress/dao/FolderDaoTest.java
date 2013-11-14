package com.noveogroup.mailexpress.dao;

import com.noveogroup.mailexpress.BasePersistenceTest;
import com.noveogroup.mailexpress.domain.Folder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Tests {@link FolderDao}.
 *
 * @author Maxim Baev
 */
public class FolderDaoTest extends BasePersistenceTest {

    private static final String OLD_FOLDER_NAME = "Junk";
    private static final String NEW_FOLDER_NAME = "Spam";

    @Autowired
    private FolderDao folderDao;

    @Test
    public void testFolderLifecycle() {
        List<Folder> folders = folderDao.findAll();
        assertNotNull(folders);
        assertEquals(5, folders.size());

        Folder folder = new Folder();
        folder.setName(OLD_FOLDER_NAME);
        folder.setSystemFolder(false);
        folder = folderDao.save(folder);
        assertNotNull(folder);
        assertNotNull(folder.getId());
        assertEquals(OLD_FOLDER_NAME, folder.getName());

        folder.setName(NEW_FOLDER_NAME);
        folder = folderDao.save(folder);
        assertNotNull(folder);
        assertEquals(NEW_FOLDER_NAME, folder.getName());

        folderDao.delete(folder);
        folders = folderDao.findAll();
        assertEquals(5, folders.size());
    }
}
