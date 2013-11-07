package com.noveogroup.mailexpress.service.impl;

import com.noveogroup.mailexpress.dao.MessageDao;
import com.noveogroup.mailexpress.model.Message;
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
public class MessageServiceImpl implements MessageService{

    @Autowired
    private MessageDao messageDao;

    @Override
    public Message saveMessage(Message message) {
        return messageDao.save(message);
    }

    @Override
    @Transactional(readOnly = true)
    public Message getMessageById(Long id) {
        return messageDao.findOne(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Long countByFolder(Long folderId) {
        return messageDao.countByFolderId(folderId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Message> findByFolder(Long folderId, String sortColumn, String direction, int pageNumber, int pageSize) {
        Pageable pageRequest;
        if (sortColumn != null) {
             pageRequest = new PageRequest(pageNumber, pageSize,Sort.Direction.fromString(direction), sortColumn);
        } else {
            pageRequest = new PageRequest(pageNumber, pageSize);
        }
        Page page = messageDao.findByFolderId(folderId, pageRequest);
        return page.getContent();
    }
}
