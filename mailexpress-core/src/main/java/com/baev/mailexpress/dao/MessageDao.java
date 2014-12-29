package com.baev.mailexpress.dao;

import com.baev.mailexpress.domain.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Message DAO.
 *
 * @author Maxim Baev
 */
@Repository("messageDao")
public interface MessageDao extends JpaRepository<Message, Long> {

    /**
     * Returns number of messages in folder with given ID.
     *
     * @param folderId folder ID
     * @return Number of messages
     */
    long countByFolderId(Long folderId);

    /**
     * Retrieves messages from folder with given ID and using provided paging parameters.
     *
     * @param folderId folder ID
     * @param pageable paging parameters
     * @return Messages in selected folder
     */
    Page<Message> findByFolderId(Long folderId, Pageable pageable);
}
