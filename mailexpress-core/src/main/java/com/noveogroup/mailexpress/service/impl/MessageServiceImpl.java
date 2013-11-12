package com.noveogroup.mailexpress.service.impl;

import com.noveogroup.mailexpress.dao.MessageDao;
import com.noveogroup.mailexpress.domain.Message;
import com.noveogroup.mailexpress.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Maxim Baev
 */
@Service("messageService")
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageDao messageDao;

    @Override
    public Message saveMessage(final Message message) {
        return messageDao.save(message);
    }

    @Override
    @Transactional(readOnly = true)
    public Message getMessageById(final Long id) {
        return messageDao.findOne(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Long countByFolder(final Long folderId) {
        return messageDao.countByFolderId(folderId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Message> findByFolder(final Long folderId, final String sortColumn, final String direction,
                                      final int pageNumber, final int pageSize) {
        Pageable pageRequest;
        if (sortColumn != null) {
            pageRequest = new PageRequest(pageNumber, pageSize, Sort.Direction.fromString(direction), sortColumn);
        } else {
            pageRequest = new PageRequest(pageNumber, pageSize);
        }
        final Page page = messageDao.findByFolderId(folderId, pageRequest);
        return page.getContent();
    }
}
