package com.noveogroup.mailexpress.service;

import com.noveogroup.mailexpress.BaseMockitoTest;
import com.noveogroup.mailexpress.dao.MessageDao;
import com.noveogroup.mailexpress.domain.Message;
import com.noveogroup.mailexpress.service.impl.MessageServiceImpl;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.*;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests {@link MessageServiceImpl} instance.
 *
 * @author Maxim Baev
 */
public class MessageServiceTest extends BaseMockitoTest {

    private static final Long TEST_FOLDER_ID = 1L;
    private static final Long TEST_MESSAGE_ID = 1L;
    private static final String TEST_SORT_COLUMN = "subject";
    private static final String TEST_DIRECTION = "asc";
    private static final int TEST_PAGE_NUM = 1;
    private static final int TEST_PAGE_SIZE = 10;
    private static final String TEST_SUBJECT = "Subj";

    @InjectMocks
    private MessageServiceImpl messageService;

    @Mock
    private MessageDao messageDao;

    @Test
    public void testCountByFolder() {
        final Long count = 10L;
        when(messageDao.countByFolderId(TEST_FOLDER_ID)).thenReturn(count);

        final Long result = messageService.countByFolder(TEST_FOLDER_ID);
        verify(messageDao).countByFolderId(TEST_FOLDER_ID);
        assertNotNull(result);
    }

    @Test
    public void testGetById() {
        final Message message = new Message();
        message.setSubject(TEST_SUBJECT);
        when(messageDao.findOne(TEST_MESSAGE_ID)).thenReturn(message);

        final Message result = messageService.getById(TEST_MESSAGE_ID);
        verify(messageDao).findOne(TEST_MESSAGE_ID);
        assertNotNull(result);
        assertEquals(TEST_SUBJECT, result.getSubject());
    }

    @Test
    public void testFindByFolder() {
        final List<Message> messages = Collections.singletonList(new Message());
        final Pageable pageRequest = new PageRequest(TEST_PAGE_NUM, TEST_PAGE_SIZE, Sort.Direction.fromString(TEST_DIRECTION), TEST_SORT_COLUMN);
        final Page<Message> page = new PageImpl(messages);
        when(messageDao.findByFolderId(eq(TEST_FOLDER_ID), eq(pageRequest))).thenReturn(page);

        final List<Message> result = messageService.findByFolder(TEST_FOLDER_ID, TEST_SORT_COLUMN, TEST_DIRECTION, TEST_PAGE_NUM, TEST_PAGE_SIZE);
        verify(messageDao).findByFolderId(eq(TEST_FOLDER_ID), eq(pageRequest));
        assertNotNull(result);
        assertEquals(messages.size(), result.size());
        assertEquals(messages.get(0), result.get(0));
    }

    @Test
    public void testSave() {
        final Message message = new Message();
        when(messageDao.save(message)).thenReturn(message);

        final Message result = messageService.save(message);
        verify(messageDao).save(message);
        assertNotNull(result);
        assertEquals(message, result);
    }

    @Test
    public void testUpdate() {
        final Message message = new Message();
        when(messageDao.save(message)).thenReturn(message);

        final Message result = messageService.update(message);
        verify(messageDao).save(message);
        assertNotNull(result);
        assertEquals(message, result);
    }

    @Test
    public void testDelete() {
        final Message message = new Message();
        doNothing().when(messageDao).delete(message);
        messageService.delete(message);
        verify(messageDao).delete(message);
    }
}
