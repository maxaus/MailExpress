package com.noveogroup.mailexpress.controller;

import com.noveogroup.mailexpress.BaseMockitoTest;
import com.noveogroup.mailexpress.dto.form.MessageFormData;
import com.noveogroup.mailexpress.service.FolderService;
import com.noveogroup.mailexpress.service.MessageService;
import org.mockito.InjectMocks;
import org.mockito.Mock;

/**
 * Tests {@link MessageController} instance.
 *
 * @author Maxim Baev
 */
public class MessageControllerTest extends BaseMockitoTest {

    @InjectMocks
    private MessageController messageController;

    @Mock
    private MessageService messageService;

    @Mock
    private FolderService folderService;

    @Mock
    private MessageFormData messageFormData;
}
