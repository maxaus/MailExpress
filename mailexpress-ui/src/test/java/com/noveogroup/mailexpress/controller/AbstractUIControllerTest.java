package com.noveogroup.mailexpress.controller;

import com.noveogroup.mailexpress.BaseMockitoTest;
import com.noveogroup.mailexpress.dto.table.MessageListData;
import com.noveogroup.mailexpress.service.FolderService;
import com.noveogroup.mailexpress.service.MessageService;
import org.mockito.Mock;

/**
 * Base test class for UI controllers.
 *
 * @author Maxim Baev
 */
public class AbstractUIControllerTest extends BaseMockitoTest {

    @Mock
    protected MessageService messageService;

    @Mock
    protected FolderService folderService;

    @Mock
    protected MessageListData messageListData;

}
