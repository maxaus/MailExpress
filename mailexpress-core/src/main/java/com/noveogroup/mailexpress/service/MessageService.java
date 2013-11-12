package com.noveogroup.mailexpress.service;

import com.noveogroup.mailexpress.domain.Message;

import java.util.List;

/**
 * @author Maxim Baev
 */
public interface MessageService {

    Message save(Message message);

    Message update(Message message);

    void delete(Message message);

    Message getById(Long id);

    Long countByFolder(Long folderId);

    List<Message> findByFolder(Long folderId, String sortColumn, String direction, int pageNumber, int pageSize);
}
