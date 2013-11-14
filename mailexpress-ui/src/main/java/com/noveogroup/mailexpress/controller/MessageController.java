package com.noveogroup.mailexpress.controller;

import com.noveogroup.mailexpress.domain.Contact;
import com.noveogroup.mailexpress.domain.ContactType;
import com.noveogroup.mailexpress.domain.Folder;
import com.noveogroup.mailexpress.domain.Message;
import com.noveogroup.mailexpress.dto.form.MessageFormData;
import com.noveogroup.mailexpress.service.FolderService;
import com.noveogroup.mailexpress.service.MessageService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.Date;

/**
 * UI controller for message manage actions.
 *
 * @author Maxim Baev
 */
@Component
@ManagedBean
@ViewScoped
public class MessageController implements Serializable {

    private static final long serialVersionUID = -4863780031482494674L;
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageController.class);
    private static final String SENT_FOLDER_NAME = "Sent";
    private static final String DEFAULT_SENDER_EMAIL = "mail@mail.com";

    private Long currentMessageItemId;

    @Autowired
    private MessageService messageService;

    @Autowired
    private FolderService folderService;

    @Autowired
    private MessageFormData messageFormData;


    public void saveMessage() {
        LOGGER.debug("Saving new message");
        final Message message = new Message();
        message.setSubject(messageFormData.getSubject());
        message.setBody(messageFormData.getBody());
        message.setDate(new Date());
        final Folder sentFolder = folderService.findByName(SENT_FOLDER_NAME);
        message.setFolder(sentFolder);
        message.setSender(new Contact(DEFAULT_SENDER_EMAIL, ContactType.SENDER));

        for (final String receiverEmail : messageFormData.getReceivers()) {
            message.addReceiver(new Contact(receiverEmail, ContactType.RECEIVER));
        }

        if (CollectionUtils.isNotEmpty(messageFormData.getCopies())) {
            for (final String receiverCopyEmail : messageFormData.getCopies()) {
                message.addReceiver(new Contact(receiverCopyEmail, ContactType.RECEIVER_COPY));
            }
        }

        messageService.save(message);
    }

    public void send() {

    }

    public void remove() {
        LOGGER.debug("Removing message. ID = {}", currentMessageItemId);
        final Message message = messageService.getById(currentMessageItemId);
        messageService.delete(message);
    }


    public void changeReadStatus() {
        LOGGER.debug("Change message read status. ID = {}", currentMessageItemId);
        final Message message = messageService.getById(currentMessageItemId);
        message.setUnread(!message.isUnread());
        messageService.save(message);
    }

    public Long getCurrentMessageItemId() {
        return currentMessageItemId;
    }

    public void setCurrentMessageItemId(final Long currentMessageItemId) {
        this.currentMessageItemId = currentMessageItemId;
    }
}
