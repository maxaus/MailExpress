package com.noveogroup.mailexpress.controller;

import com.noveogroup.mailexpress.BaseMockitoTest;
import com.noveogroup.mailexpress.dto.form.FolderFormData;
import com.noveogroup.mailexpress.service.FolderService;
import org.mockito.InjectMocks;
import org.mockito.Mock;

/**
 * Tests {@link FolderController} instance.
 * @author Maxim Baev
 */
public class FolderControllerTest extends BaseMockitoTest {

    @InjectMocks
    private FolderController folderController;

    @Mock
    private FolderService folderService;

    @Mock
    private FolderFormData folderFormData;


}
