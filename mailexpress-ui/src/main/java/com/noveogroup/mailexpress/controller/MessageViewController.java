package com.noveogroup.mailexpress.controller;

import com.noveogroup.mailexpress.dto.MessageItem;
import com.noveogroup.mailexpress.dto.table.MessageListData;
import com.noveogroup.mailexpress.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;

/**
 * @author Maxim Baev
 */
@Component
@ManagedBean
@ViewScoped
public class MessageViewController implements Serializable {

    private static final long serialVersionUID = -4863780031482494674L;

    @Autowired
    private MessageListData messageListData;

    @Autowired
    private MessageService messageService;

    public MessageItem getMessage() {
        return messageListData.getSelectedItem();
    }

}
