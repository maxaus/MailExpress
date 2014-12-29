package com.baev.mailexpress.dao;

import com.baev.mailexpress.BasePersistenceTest;
import com.baev.mailexpress.domain.Attachment;
import com.baev.mailexpress.domain.Contact;
import com.baev.mailexpress.domain.Folder;
import com.baev.mailexpress.domain.Message;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Tests {@link MessageDao}.
 *
 * @author Maxim Baev
 */
public class MessageDaoTest extends BasePersistenceTest {

    private static final String SUBJECT = "Subj";

    @Autowired
    private MessageDao messageDao;

    @Autowired
    private FolderDao folderDao;

    @Test
    public void testMessageLifeCycle() {
        List<Message> messages = messageDao.findAll();
        assertNotNull(messages);
        assertEquals(1, messages.size());

        Message message = new Message();
        message.setSubject(SUBJECT);
        message.setBody("body");
        Attachment attachment = new Attachment();
        attachment.setPath("img.png");
        message.addAttachment(attachment);
        Contact receiver = new Contact();
        receiver.setEmail("mail@mail.com");
        message.setReceivers(Collections.singletonList(receiver));
        Contact sender = new Contact();
        sender.setEmail("mail2@mail.com");
        message.setSender(sender);
        final Folder folder = folderDao.findOne(1L);
        message.setFolder(folder);
        message = messageDao.save(message);
        assertNotNull(message);
        assertNotNull(message.getId());
        assertEquals(SUBJECT, message.getSubject());

        messageDao.delete(message);
        messages = messageDao.findAll();
        assertEquals(1, messages.size());
    }
}
