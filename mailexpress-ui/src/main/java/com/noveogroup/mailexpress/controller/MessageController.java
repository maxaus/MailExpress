package com.noveogroup.mailexpress.controller;

import com.noveogroup.mailexpress.domain.Contact;
import com.noveogroup.mailexpress.domain.ContactType;
import com.noveogroup.mailexpress.domain.Folder;
import com.noveogroup.mailexpress.domain.Message;
import com.noveogroup.mailexpress.dto.form.MessageFormData;
import com.noveogroup.mailexpress.service.FolderService;
import com.noveogroup.mailexpress.service.MessageService;
import org.apache.commons.collections.CollectionUtils;
import org.richfaces.event.FileUploadEvent;
import org.richfaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.*;

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
    private static final String BUNDLE_NAME = "MailExpress";
    private static final String FORWARD_PREFIX = "FW: ";
    private static final String REPLY_PREFIX = "RE: ";
    private static final String SENT_FOLDER_NAME = "Sent";
    private static final String DEFAULT_SENDER_EMAIL = "mail@mail.com";

    private Long currentMessageItemId;

    private String actionName;

    @Autowired
    private MessageService messageService;

    @Autowired
    private FolderService folderService;

    private MessageFormData messageFormData = new MessageFormData();

    public void send() {
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
        //TODO: uncomment
//        messageService.save(message);
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

    public void uploadListener(final FileUploadEvent event) throws Exception {
        UploadedFile item = event.getUploadedFile();
//        byte[] content = item.getData();
//        String fileName = item.getName();

        messageFormData.getAttachments().add(item);

    }

    public void openForm() {
        Message item = null;
        if (currentMessageItemId != null && !"create".equals(actionName)) {
            messageFormData.clear();
            item = messageService.getById(currentMessageItemId);
        }

        switch (actionName) {
            case "create":
                LOGGER.debug("Open form to create new message.");
                messageFormData.setTitle(ResourceBundle.getBundle(BUNDLE_NAME,
                        FacesContext.getCurrentInstance().getViewRoot().getLocale()).getString("new_message"));

                break;
            case "forward":
                LOGGER.debug("Forward message. ID={}", currentMessageItemId);
                messageFormData.setTitle(ResourceBundle.getBundle(BUNDLE_NAME,
                        FacesContext.getCurrentInstance().getViewRoot().getLocale()).getString("forward"));
                if (item != null) {
                    messageFormData.setSubject(FORWARD_PREFIX + item.getSubject());
                    messageFormData.setBody(item.getBody());

                }
                break;
            case "reply":
                LOGGER.debug("Reply on message. ID={}", currentMessageItemId);
                messageFormData.setTitle(ResourceBundle.getBundle(BUNDLE_NAME,
                        FacesContext.getCurrentInstance().getViewRoot().getLocale()).getString("reply"));
                if (item != null) {
                    messageFormData.setSubject(REPLY_PREFIX + item.getSubject());
                    messageFormData.setBody(item.getBody());
                    messageFormData.setReceivers(Collections.singletonList(item.getSender().getEmail()));
                }
                break;
            case "reply_to_all":
                LOGGER.debug("Reply to all on message. ID={}", currentMessageItemId);
                messageFormData.setTitle(ResourceBundle.getBundle(BUNDLE_NAME,
                        FacesContext.getCurrentInstance().getViewRoot().getLocale()).getString("reply_to_all"));
                if (item != null) {
                    messageFormData.setSubject(REPLY_PREFIX + item.getSubject());
                    messageFormData.setBody(item.getBody());
                    List<String> receivers = new ArrayList<>();
                    receivers.add(item.getSender().getEmail());
                    for (Contact contact : item.getReceivers()) {
                        receivers.add(contact.getEmail());
                    }
                    messageFormData.setReceivers(receivers);
                }
                break;
        }
    }

    public Long getCurrentMessageItemId() {
        return currentMessageItemId;
    }

    public void setCurrentMessageItemId(final Long currentMessageItemId) {
        this.currentMessageItemId = currentMessageItemId;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(final String actionName) {
        this.actionName = actionName;
    }

    public MessageFormData getMessageFormData() {
        return messageFormData;
    }

    public void setMessageFormData(final MessageFormData messageFormData) {
        this.messageFormData = messageFormData;
    }
}
