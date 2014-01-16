package com.noveogroup.mailexpress.controller;

import com.noveogroup.mailexpress.domain.Folder;
import com.noveogroup.mailexpress.domain.Message;
import com.noveogroup.mailexpress.dto.AttachmentDto;
import org.junit.Test;
import org.mockito.InjectMocks;

import java.util.Collections;

import static org.mockito.Matchers.any;
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
    private static final String INBOX_FOLDER_NAME = "Inbox";
    private static final String SENT_FOLDER_NAME = "Sent";

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
        messageController.changeReadStatus();
    }

    @Test
    public void testMoveToOtherFolder() {
        messageController.moveToOtherFolder();
    }

    @Test
    public void testDeleteAttachment() {
        messageController.deleteAttachment();
    }
}
