package com.noveogroup.mailexpress.service;

import com.noveogroup.mailexpress.model.Message;

import java.util.List;

/**
 * @author Maxim Baev
 */
public interface MessageService {

    List<Message> findAll();
}
