package com.baev.mailexpress.service;

import com.baev.mailexpress.domain.Message;

import java.util.Collection;
import java.util.List;

/**
 * Service class that contains business logic for managing messages.
 *
 * @author Maxim Baev
 */
public interface MessageService {

    /**
     * Persists message.
     *
     * @param message message
     * @return Saved message
     */
    Message save(Message message);

    /**
     * Updates message.
     *
     * @param message message
     * @return Updated message
     */
    Message update(Message message);

    /**
     * Removes messages.
     *
     * @param ids message IDs
     */
    void deleteAll(Collection<Long> ids);

    /**
     * Retrieves message by provided ID.
     *
     * @param id ID
     * @return Found message
     */
    Message getById(Long id);

    /**
     * Retrieves messages by provided ID list.
     *
     * @param ids IDs
     * @return Found messages
     */
    List<Message> getByIds(Collection<Long> ids);

    /**
     * Retrieves number of messages in folder.
     *
     * @param folderId folder ID
     * @return Messages count
     */
    Long countByFolder(Long folderId);

    /**
     * Retrieves messages in folder using paging parameters.
     *
     * @param folderId   folder ID
     * @param sortColumn sorting column name
     * @param direction  sorting direction
     * @param pageNumber page number
     * @param pageSize   number of entries on the page
     * @return List of messages
     */
    List<Message> findByFolder(Long folderId, String sortColumn, String direction, int pageNumber, int pageSize);
}
