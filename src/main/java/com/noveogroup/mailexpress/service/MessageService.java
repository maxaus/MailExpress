package com.noveogroup.mailexpress.service;

import com.noveogroup.mailexpress.model.Message;

import java.util.List;

/**
 * @author Maxim Baev
 */
public interface MessageService {

    List<Message> findAll();

    Long getCount();

    Message getMessageById(Long id);

    List<Message> find(Long folderId, String sortColumn, String direction, int pageNumber, int pageSize);
}
