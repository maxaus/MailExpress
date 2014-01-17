package com.noveogroup.mailexpress.controller;

import com.noveogroup.mailexpress.domain.Folder;
import com.noveogroup.mailexpress.domain.Message;
import com.noveogroup.mailexpress.dto.AttachmentDto;
import org.junit.Test;
import org.mockito.InjectMocks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.*;
import static org.mockito.Matchers.anyCollection;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests {@link MessageController} instance.
 *
 * @author Maxim Baev
 */
public class MessageControllerTest extends AbstractUIControllerTest {

    private static final String TEST_MESSAGE_BODY = "abscdef";
    private static final String TEST_EMAIL = "mail@mail.net";
    private static final String TEST_EMAIL2 = "mail2@mail.net";
    private static final String TEST_MESSAGE_SUBJECT = "subject";
    private static final String TEST_FILENAME = "1.doc";
    private static final String INBOX_FOLDER_NAME = "Inbox";
    private static final String SENT_FOLDER_NAME = "Sent";
    private static final String FILE_PARAM_KEY = "file";

    @InjectMocks
    private MessageController messageController;

    @Test
    public void testOpenForm() {
//        messageController.openForm();
    }

    @Test
    public void testSend() {
        messageController.getMessageFormData().setBody(TEST_MESSAGE_BODY);
        messageController.getMessageFormData().setReceivers(Collections.singletonList(TEST_EMAIL));
        messageController.getMessageFormData().setCopies(Collections.singletonList(TEST_EMAIL2));
        messageController.getMessageFormData().setSubject(TEST_MESSAGE_SUBJECT);
        AttachmentDto attachmentDto = new AttachmentDto();

        messageController.getMessageFormData().setAttachments(Collections.singletonList(attachmentDto));

        when(folderService.findByName(SENT_FOLDER_NAME)).thenReturn(new Folder());
        when(messageService.save(any(Message.class))).thenReturn(new Message());
        messageController.send();
        verify(folderService).findByName(SENT_FOLDER_NAME);
        verify(messageService).save(any(Message.class));
    }

    @Test
    public void testSendReceive() {
        when(folderService.findByName(INBOX_FOLDER_NAME)).thenReturn(new Folder());
        when(messageService.save(any(Message.class))).thenReturn(new Message());
        messageController.sendReceive();
        verify(folderService).findByName(INBOX_FOLDER_NAME);
        verify(messageService).save(any(Message.class));
    }

    @Test
    public void testRemoveMessages() {
        messageListData.setSelectedIndexes(Collections.singletonList(1L));
        messageController.removeMessages();
    }

    @Test
    public void testChangeReadStatus() {
        final Message message = new Message();
        message.setUnread(true);
        when(messageService.getById(anyLong())).thenReturn(message);
        when(messageService.save(message)).thenReturn(message);
        messageController.changeReadStatus();

        assertFalse(message.isUnread());
        verify(messageService).getById(anyLong());
        verify(messageService).save(message);
    }

    @Test
    public void testMoveToOtherFolder() {
        final Message message = new Message();
        when(messageService.getByIds(anyCollection())).thenReturn(Collections.singletonList(message));
        when(folderService.findByName(anyString())).thenReturn(new Folder());
        when(messageService.update(message)).thenReturn(message);
        messageController.moveToOtherFolder();
        verify(messageService).getByIds(anyCollection());
        verify(folderService).findByName(anyString());
        verify(messageService).update(message);
    }

    @Test
    public void testDeleteAttachment() {
        fcRequestMap.put(FILE_PARAM_KEY, TEST_FILENAME);
        final AttachmentDto attachmentDto = new AttachmentDto();
        attachmentDto.setPath(TEST_FILENAME);
        final List<AttachmentDto> attachmentDtoList = new ArrayList<>();
        attachmentDtoList.add(attachmentDto);
        messageController.getMessageFormData().setAttachments(attachmentDtoList);
        messageController.deleteAttachment();
        assertEquals(0, messageController.getMessageFormData().getAttachments().size());
    }
}
