package com.noveogroup.mailexpress.controller;

import com.noveogroup.mailexpress.domain.Attachment;
import com.noveogroup.mailexpress.domain.Contact;
import com.noveogroup.mailexpress.domain.ContactType;
import com.noveogroup.mailexpress.domain.Folder;
import com.noveogroup.mailexpress.domain.Message;
import com.noveogroup.mailexpress.dto.AttachmentDto;
import com.noveogroup.mailexpress.dto.form.MessageFormData;
import com.noveogroup.mailexpress.dto.table.MessageListData;
import com.noveogroup.mailexpress.service.FolderService;
import com.noveogroup.mailexpress.service.MessageService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.richfaces.event.FileUploadEvent;
import org.richfaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

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
    private static final String INBOX_FOLDER_NAME = "Inbox";
    private static final String SENT_FOLDER_NAME = "Sent";
    private static final String DEFAULT_SENDER_EMAIL = "mail@mail.com";

    private Long currentMessageItemId;

    private String actionName;

    @Autowired
    private MessageService messageService;

    @Autowired
    private FolderService folderService;

    @Autowired
    private MessageListData messageListData;

    @Value("${upload.dir}")
    private String uploadDir;

    private MessageFormData messageFormData = new MessageFormData();

    private String targetFolderName;

    //TODO: move to application scoped controller
    private List<String> folderNames = new ArrayList<>();

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
     * Remove void.
     */
    public void remove() {
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
     * @throws Exception the exception
     */
    public void uploadListener(final FileUploadEvent event) throws Exception {
        try {
            UploadedFile item = event.getUploadedFile();
            File file = new File(uploadDir + item.getName());
            FileUtils.writeByteArrayToFile(file, item.getData());
            messageFormData.getAttachments().add(new AttachmentDto(item.getName()));
        } catch (IOException e) {
            LOGGER.error("Problem while saving file.", e);
            throw e;
        }
    }

    /**
     * Open form.
     */
    public void openForm() {
        Message item = null;
        messageFormData = new MessageFormData();
        if (currentMessageItemId != null && !"create".equals(actionName)) {
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
                    if (CollectionUtils.isNotEmpty(item.getAttachments())) {
                        final List<AttachmentDto> attachmentDtoList = new ArrayList<>();
                        for (Attachment attachment : item.getAttachments()) {
                            attachmentDtoList.add(new AttachmentDto(attachment.getPath()));
                        }
                        messageFormData.setAttachments(attachmentDtoList);
                    }
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

    public void moveToOtherFolder() {
        LOGGER.debug("Moving messages with IDs={} to the folder {}", messageListData.getSelectedIndexes(), targetFolderName);
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
        String filename = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("file");
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

    /**
     * Gets folder names.
     *
     * @return the folder names
     */
    public List<String> getFolderNames() {
        if (CollectionUtils.isEmpty(folderNames)) {
            final List<Folder> folders = folderService.findAll();
            for (final Folder folder : folders) {
                folderNames.add(folder.getName());
            }
        }
        return folderNames;
    }

    /**
     * Sets folder names.
     *
     * @param folderNames the folder names
     */
    public void setFolderNames(final List<String> folderNames) {
        this.folderNames = folderNames;
    }

    public String downloadFile() {
        String filename = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("file");
        File file = new File(uploadDir + filename);
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

        writeOutContent(response, file, file.getName());

        FacesContext.getCurrentInstance().responseComplete();
        return null;
    }

    private void writeOutContent(final HttpServletResponse res, final File content, final String theFilename) {
        if (content == null) {
            return;
        }
        try {
            res.setHeader("Pragma", "no-cache");
            res.setDateHeader("Expires", 0);
            res.setHeader("Content-disposition", "attachment; filename=" + theFilename);
            FileInputStream fis = new FileInputStream(content);
            ServletOutputStream os = res.getOutputStream();
            int bt = fis.read();
            while (bt != -1) {
                os.write(bt);
                bt = fis.read();
            }
            os.flush();
            fis.close();
            os.close();
        } catch (final IOException ex) {
            LOGGER.error("", ex);
        }
    }
}
