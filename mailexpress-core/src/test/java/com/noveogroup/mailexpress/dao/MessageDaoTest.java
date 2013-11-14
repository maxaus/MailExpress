package com.noveogroup.mailexpress.dao;

import com.noveogroup.mailexpress.BasePersistenceTest;
import com.noveogroup.mailexpress.domain.Attachment;
import com.noveogroup.mailexpress.domain.Contact;
import com.noveogroup.mailexpress.domain.Folder;
import com.noveogroup.mailexpress.domain.Message;
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

    private static final String OLD_SUBJECT = "Subj";
    private static final String NEW_SUBJECT = "Subject";

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
        message.setSubject(OLD_SUBJECT);
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
        assertEquals(OLD_SUBJECT, message.getSubject());

        message.setSubject(NEW_SUBJECT);
        message = messageDao.save(message);
        assertEquals(NEW_SUBJECT, message.getSubject());

        messageDao.delete(message);
        messages = messageDao.findAll();
        assertEquals(1, messages.size());
    }
}
