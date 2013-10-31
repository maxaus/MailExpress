package com.noveogroup.mailexpress.controller;

import com.noveogroup.mailexpress.dto.MessageItem;
import com.noveogroup.mailexpress.model.Message;
import com.noveogroup.mailexpress.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Maxim Baev
 */
@Component
@ManagedBean
@SessionScoped
public class MessageController {

    @Autowired
    private MessageService messageService;

    public List<MessageItem> getMessages() {
//        return messageService.findAll();
        return new ArrayList<MessageItem>();
    }
}
