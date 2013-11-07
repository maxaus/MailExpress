package com.noveogroup.mailexpress.service;

import com.noveogroup.mailexpress.model.Message;

import java.util.List;

/**
 * @author Maxim Baev
 */
public interface MessageService {

    Message saveMessage(Message message);

    Message getMessageById(Long id);

    Long countByFolder(Long folderId);

    List<Message> findByFolder(Long folderId, String sortColumn, String direction, int pageNumber, int pageSize);
}
