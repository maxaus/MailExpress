package com.baev.mailexpress.controller;

import com.baev.mailexpress.dto.table.MessageListData;
import com.baev.mailexpress.service.FolderService;
import com.baev.mailexpress.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

/**
 * @author Maxim Baev
 */
public abstract class AbstractUIController implements Serializable {

    protected static final String BUNDLE_NAME = "MailExpress";
    private static final long serialVersionUID = -574120531291344501L;

    @Autowired
    protected MessageService messageService;

    @Autowired
    protected FolderService folderService;

    @Autowired
    protected MessageListData messageListData;
}
