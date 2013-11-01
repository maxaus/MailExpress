package com.noveogroup.mailexpress.controller;

import com.noveogroup.mailexpress.dto.MessageItem;
import com.noveogroup.mailexpress.model.Contact;
import com.noveogroup.mailexpress.model.Message;
import com.noveogroup.mailexpress.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Maxim Baev
 */
@Component
@ManagedBean
@SessionScoped
public class MessageController {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");

    @Autowired
    private MessageService messageService;

    public List<MessageItem> getMessages() {
        List<Message> messages = messageService.findAll();
        List<MessageItem> itemList = new ArrayList<MessageItem>(messages.size());
        for (Message message : messages) {
            MessageItem item = new MessageItem();
            item.setId(message.getId());
            item.setSubject(message.getSubject());

            Contact sender = message.getSender();
            if (sender != null) {
                if (!StringUtils.isEmpty(sender.getName())) {
                    item.setSender(sender.getName());
                } else {
                    item.setSender(sender.getEmail());
                }
            }
            if (message.getDate() != null) {
                item.setDate(DATE_FORMAT.format(message.getDate()));
            }
            itemList.add(item);
        }
        return itemList;
    }
}
