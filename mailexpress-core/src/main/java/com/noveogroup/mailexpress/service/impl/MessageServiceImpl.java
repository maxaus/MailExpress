package com.noveogroup.mailexpress.service.impl;

import com.noveogroup.mailexpress.dao.MessageDao;
import com.noveogroup.mailexpress.domain.Message;
import com.noveogroup.mailexpress.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    @Transactional
    public Message save(final Message message) {
        LOGGER.info("Saving new message");
        return messageDao.save(message);
    }

    @Override
    @Transactional
    public Message update(final Message message) {
        LOGGER.info("Updating message with ID = {}", message.getId());
        return messageDao.save(message);
    }

    @Override
    @Transactional
    public void delete(final Message message) {
        LOGGER.info("Removing message with ID = {}", message.getId());
        messageDao.delete(message);
    }

    @Override
    @Transactional(readOnly = true)
    public Message getById(final Long id) {
        LOGGER.info("Retrieving message with ID = {}", id);
        return messageDao.findOne(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Long countByFolder(final Long folderId) {
        LOGGER.info("Counting messages in folder with ID = {}", folderId);
        return messageDao.countByFolderId(folderId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Message> findByFolder(final Long folderId, final String sortColumn, final String direction,
                                      final int pageNumber, final int pageSize) {
        LOGGER.info("Retrieving messages in folder. folderId = {}, sortColumn = {}, direction = {}, pageNumber = {}, pageSize = {}", folderId, sortColumn, direction, pageNumber, pageSize);
        final Pageable pageRequest;
        if (sortColumn != null) {
            pageRequest = new PageRequest(pageNumber, pageSize, Sort.Direction.fromString(direction), sortColumn);
        } else {
            pageRequest = new PageRequest(pageNumber, pageSize);
        }
        final Page page = messageDao.findByFolderId(folderId, pageRequest);
        return page.getContent();
    }
}
