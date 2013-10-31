package com.noveogroup.mailexpress.service.impl;

import com.noveogroup.mailexpress.dao.MessageDao;
import com.noveogroup.mailexpress.model.Message;
import com.noveogroup.mailexpress.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Transactional(readOnly = true)
    public List<Message> findAll() {
        return messageDao.findAll();
    }
}
