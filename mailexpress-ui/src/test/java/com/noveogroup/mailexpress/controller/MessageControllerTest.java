package com.noveogroup.mailexpress.controller;

import com.noveogroup.mailexpress.dto.AttachmentDto;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Collections;

/**
 * Tests {@link MessageController} instance.
 *
 * @author Maxim Baev
 */
public class MessageControllerTest extends AbstractUIControllerTest {

    private static final String TEST_MESSAGE_BODY = "abscdef";
    private static final String TEST_EMAIL = "mail@mail.net";
    private static final String TEST_MESSAGE_SUBJECT = "subject";

    @InjectMocks
    private MessageController messageController;

    @Mock
    private String uploadDir;

    @Test
    public void testOpenForm() {
//        messageController.openForm();
    }

    @Test
    public void testSend() {
        messageController.getMessageFormData().setBody(TEST_MESSAGE_BODY);
        messageController.getMessageFormData().setReceivers(Collections.singletonList(TEST_EMAIL));
        messageController.getMessageFormData().setSubject(TEST_MESSAGE_SUBJECT);
        AttachmentDto attachmentDto = new AttachmentDto();
        messageController.getMessageFormData().setAttachments(Collections.singletonList(attachmentDto));

        messageController.send();
    }

    @Test
    public void testSendReceive() {
        messageController.sendReceive();
    }

    @Test
    public void testRemoveMessages() {
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
