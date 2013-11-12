package com.noveogroup.mailexpress.service;

import com.noveogroup.mailexpress.BaseMockitoTest;
import com.noveogroup.mailexpress.dao.FolderDao;
import com.noveogroup.mailexpress.domain.Folder;
import com.noveogroup.mailexpress.service.impl.FolderServiceImpl;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests {@link FolderServiceImpl} instance.
 *
 * @author Maxim Baev
 */
public class FolderServiceTest extends BaseMockitoTest {

    private static final String FOLDER_NAME = "Inbox";

    @InjectMocks
    private FolderServiceImpl folderService;

    @Mock
    private FolderDao folderDao;

    @Test
    public void testFindAll() {
        final Folder folder = new Folder();
        folder.setName(FOLDER_NAME);
        when(folderDao.findAll()).thenReturn(Collections.singletonList(folder));
        final List<Folder> result = folderService.findAll();
        verify(folderDao).findAll();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(FOLDER_NAME, result.get(0).getName());
    }

    @Test
    public void testSave() {
        final Folder folder = new Folder();
        when(folderDao.save(folder)).thenReturn(folder);

        final Folder result = folderService.save(folder);
        verify(folderDao).save(folder);
        assertNotNull(result);
        assertEquals(folder, result);
    }

    @Test
    public void testUpdate() {
        final Folder folder = new Folder();
        when(folderDao.save(folder)).thenReturn(folder);

        final Folder result = folderService.update(folder);
        verify(folderDao).save(folder);
        assertNotNull(result);
        assertEquals(folder, result);
    }

    @Test
    public void testDelete() {
        final Folder folder = new Folder();
        doNothing().when(folderDao).delete(folder);
        folderService.delete(folder);
        verify(folderDao).delete(folder);
    }
}
