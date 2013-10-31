package com.noveogroup.mailexpress.dao;

import com.noveogroup.mailexpress.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Maxim Baev
 */
@Repository("messageDao")
public interface MessageDao extends JpaRepository<Message, Long> {
}
