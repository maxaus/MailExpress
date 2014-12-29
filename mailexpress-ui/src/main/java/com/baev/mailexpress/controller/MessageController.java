package com.baev.mailexpress.controller;

import com.baev.mailexpress.domain.*;
import com.baev.mailexpress.dto.AttachmentDto;
import com.baev.mailexpress.dto.form.MessageFormData;
import com.baev.mailexpress.util.attachment.AttachmentUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.richfaces.event.FileUploadEvent;
import org.richfaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * UI controller for message manage actions.
 *
 * @author Maxim Baev
 */
@Component
@ManagedBean
@ViewScoped
public class MessageController extends AbstractUIController {

    private static final long serialVersionUID = -4863780031482494674L;
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageController.class);

    private static final String FILE_PARAM_KEY = "file";
    private static final String FORWARD_PREFIX = "FW: ";
    private static final String REPLY_PREFIX = "RE: ";
    private static final String INBOX_FOLDER_NAME = "Inbox";
    private static final String SENT_FOLDER_NAME = "Sent";
    private static final String DEFAULT_SENDER_EMAIL = "mail@mail.com";
    private static final String CREATE_MESSAGE_ACTION = "new_message";
    private static final String FORWARD_MESSAGE_ACTION = "forward";
    private static final String REPLY_MESSAGE_ACTION = "reply";
    private static final String REPLY_TO_ALL_MESSAGE_ACTION = "reply_to_all";

    private Long currentMessageItemId;
    private String actionName;
    private MessageFormData messageFormData = new MessageFormData();
    private String targetFolderName;

    @Value("${upload.dir}")
    private String uploadDir;

    /**
     * Send void.
     */
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

        for (final AttachmentDto attachmentDto : messageFormData.getAttachments()) {
            message.addAttachment(new Attachment(attachmentDto.getPath()));
        }

        messageService.save(message);
    }

    /**
     * It's a fake "send-receive" operation that creates 1 new incoming message and puts it in Inbox folder.
     */
    public void sendReceive() {
        LOGGER.debug("Send and receive all messages");
        final Message message = new Message();
        message.setSubject("Some subject " + RandomUtils.nextInt());
        message.setBody("");
        message.setUnread(true);
        message.setDate(new Date());
        final Folder inboxFolder = folderService.findByName(INBOX_FOLDER_NAME);
        message.setFolder(inboxFolder);
        message.setSender(new Contact(RandomUtils.nextInt() + DEFAULT_SENDER_EMAIL, ContactType.SENDER));
        message.addReceiver(new Contact(DEFAULT_SENDER_EMAIL, ContactType.RECEIVER));

        messageService.save(message);
    }

    /**
     * Removes messages.
     */
    public void removeMessages() {
        LOGGER.debug("Removing messages. IDs = {}", messageListData.getSelectedIndexes());
        messageService.deleteAll(messageListData.getSelectedIndexes());
    }


    /**
     * Change read status.
     */
    public void changeReadStatus() {
        LOGGER.debug("Change message read status. ID = {}", currentMessageItemId);
        final Message message = messageService.getById(currentMessageItemId);
        message.setUnread(!message.isUnread());
        messageService.save(message);
    }

    /**
     * Upload listener.
     *
     * @param event the event
     * @throws IOException exception while saving attachment
     */
    public void uploadListener(final FileUploadEvent event) throws IOException {
        final UploadedFile item = event.getUploadedFile();
        AttachmentUtil.saveAttachment(uploadDir, item);
        messageFormData.getAttachments().add(new AttachmentDto(item.getName()));

    }

    /**
     * Prepares appropriate data for message form being opened.
     */
    public void openForm() {
        LOGGER.debug("Open folder form. Action: {}", actionName);
        Message message = null;
        messageFormData = new MessageFormData();
        if (currentMessageItemId != null && !CREATE_MESSAGE_ACTION.equals(actionName)) {
            message = messageService.getById(currentMessageItemId);
            messageFormData.setBody(message.getBody());
            updateMesssageData(message);
        }
        messageFormData.setTitle(ResourceBundle.getBundle(BUNDLE_NAME,
                FacesContext.getCurrentInstance().getViewRoot().getLocale()).getString(actionName));
    }



    public void moveToOtherFolder() {
        LOGGER.debug("Moving messages with IDs={} to the folder {}", messageListData.getSelectedIndexes(),
                targetFolderName);
        final List<Message> messages = messageService.getByIds(messageListData.getSelectedIndexes());
        final Folder folder = folderService.findByName(targetFolderName);
        if (folder != null) {
            for (Message message : messages) {
                message.setFolder(folder);
                messageService.update(message);
            }
        }
    }

    public void deleteAttachment() {
        final String filename = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
                .get(FILE_PARAM_KEY);
        AttachmentDto attachmentToRemove = null;
        for (AttachmentDto attachmentDto : messageFormData.getAttachments()) {
            if (filename.equals(attachmentDto.getPath())) {
                attachmentToRemove = attachmentDto;
            }
        }
        messageFormData.getAttachments().remove(attachmentToRemove);
    }

    /**
     * Gets current message item id.
     *
     * @return the current message item id
     */
    public Long getCurrentMessageItemId() {
        return currentMessageItemId;
    }

    /**
     * Sets current message item id.
     *
     * @param currentMessageItemId the current message item id
     */
    public void setCurrentMessageItemId(final Long currentMessageItemId) {
        this.currentMessageItemId = currentMessageItemId;
    }

    /**
     * Gets action name.
     *
     * @return the action name
     */
    public String getActionName() {
        return actionName;
    }

    /**
     * Sets action name.
     *
     * @param actionName the action name
     */
    public void setActionName(final String actionName) {
        this.actionName = actionName;
    }

    /**
     * Gets message form data.
     *
     * @return the message form data
     */
    public MessageFormData getMessageFormData() {
        return messageFormData;
    }

    /**
     * Sets message form data.
     *
     * @param messageFormData the message form data
     */
    public void setMessageFormData(final MessageFormData messageFormData) {
        this.messageFormData = messageFormData;
    }

    /**
     * Gets selected folder name.
     *
     * @return the selected folder name
     */
    public String getTargetFolderName() {
        return targetFolderName;
    }

    /**
     * Sets selected folder name.
     *
     * @param targetFolderName the selected folder name
     */
    public void setTargetFolderName(final String targetFolderName) {
        this.targetFolderName = targetFolderName;
    }


    public String downloadFile() throws IOException {
        final String filename = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
                .get(FILE_PARAM_KEY);
        final HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance()
                .getExternalContext().getResponse();

        AttachmentUtil.writeAttachmentToResponse(response, uploadDir, filename);

        FacesContext.getCurrentInstance().responseComplete();
        return null;
    }

    private void updateMesssageData(final Message message) {
        switch (actionName) {
            case FORWARD_MESSAGE_ACTION:
                LOGGER.debug("Forward message. ID={}", currentMessageItemId);
                if (message != null) {
                    messageFormData.setSubject(FORWARD_PREFIX + message.getSubject());
                    if (CollectionUtils.isNotEmpty(message.getAttachments())) {
                        final List<AttachmentDto> attachmentDtoList = new ArrayList<>();
                        for (Attachment attachment : message.getAttachments()) {
                            attachmentDtoList.add(new AttachmentDto(attachment.getPath()));
                        }
                        messageFormData.setAttachments(attachmentDtoList);
                    }
                }
                break;
            case REPLY_MESSAGE_ACTION:
                LOGGER.debug("Reply on message. ID={}", currentMessageItemId);
                if (message != null) {
                    messageFormData.setSubject(REPLY_PREFIX + message.getSubject());
                    messageFormData.setReceivers(Collections.singletonList(message.getSender().getEmail()));
                }
                break;
            case REPLY_TO_ALL_MESSAGE_ACTION:
                LOGGER.debug("Reply to all on message. ID={}", currentMessageItemId);
                if (message != null) {
                    messageFormData.setSubject(REPLY_PREFIX + message.getSubject());
                    final List<String> receivers = new ArrayList<>();
                    receivers.add(message.getSender().getEmail());
                    for (Contact contact : message.getReceivers()) {
                        receivers.add(contact.getEmail());
                    }
                    messageFormData.setReceivers(receivers);
                }
                break;
            default:
                break;
        }
    }
}
