package com.baev.mailexpress.service.impl;

import com.baev.mailexpress.dao.MessageDao;
import com.baev.mailexpress.domain.Message;
import com.baev.mailexpress.service.MessageService;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * Implementation of the {@link MessageService}.
 *
 * @author Maxim Baev
 */
@Service("messageService")
public class MessageServiceImpl implements MessageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FolderServiceImpl.class);

    @Autowired
    private MessageDao messageDao;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Message save(final Message message) {
        LOGGER.info("Saving new message");
        return messageDao.save(message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Message update(final Message message) {
        LOGGER.info("Updating message with ID = {}", message.getId());
        return messageDao.save(message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void deleteAll(final Collection<Long> ids) {
        LOGGER.info("Removing messages with IDs = {}", ids);
        final List<Message> messagesToDelete = messageDao.findAll(ids);
        messageDao.delete(messagesToDelete);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Message getById(final Long id) {
        LOGGER.info("Retrieving message with ID = {}", id);
        final Message message = messageDao.findOne(id);
        if (message != null) {
            Hibernate.initialize(message.getReceivers());
            Hibernate.initialize(message.getAttachments());
        }
        return message;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<Message> getByIds(final Collection<Long> ids) {
        return messageDao.findAll(ids);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Long countByFolder(final Long folderId) {
        LOGGER.info("Counting messages in folder with ID = {}", folderId);
        return messageDao.countByFolderId(folderId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<Message> findByFolder(final Long folderId, final String sortColumn, final String direction,
                                      final int pageNumber, final int pageSize) {
        LOGGER.info("Retrieving messages in folder. folderId = {}, sortColumn = {}, direction = {}, pageNumber = {}, "
                + "pageSize = {}", folderId, sortColumn, direction, pageNumber, pageSize);
        final Pageable pageRequest;
        if (sortColumn != null) {
            pageRequest = new PageRequest(pageNumber, pageSize, Sort.Direction.fromString(direction), sortColumn);
        } else {
            pageRequest = new PageRequest(pageNumber, pageSize);
        }
        final Page page = messageDao.findByFolderId(folderId, pageRequest);
        final List<Message> messages = page.getContent();
        for (final Message message : messages) {
            Hibernate.initialize(message.getAttachments());
        }
        return messages;
    }
}
