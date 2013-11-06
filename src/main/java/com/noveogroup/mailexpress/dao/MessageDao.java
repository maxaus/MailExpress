package com.noveogroup.mailexpress.dao;

import com.noveogroup.mailexpress.model.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Maxim Baev
 */
@Repository("messageDao")
public interface MessageDao extends JpaRepository<Message, Long> {

    long countByFolderId(Long folderId);

    Page<Message> findByFolderId(Long folderId, Pageable pageable);
}
