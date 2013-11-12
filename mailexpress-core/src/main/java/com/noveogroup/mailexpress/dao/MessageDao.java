package com.noveogroup.mailexpress.dao;

import com.noveogroup.mailexpress.domain.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Maxim Baev
 */
@Repository("messageDao")
public interface MessageDao extends JpaRepository<Message, Long> {

    long countByFolderId(Long folderId);

    Page<Message> findByFolderId(Long folderId, Pageable pageable);
}
