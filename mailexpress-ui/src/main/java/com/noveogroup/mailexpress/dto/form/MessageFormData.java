package com.noveogroup.mailexpress.dto.form;

import org.richfaces.model.UploadedFile;
import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.util.List;

/**
 * @author Maxim Baev
 */
@Component
@ManagedBean
@RequestScoped
public class MessageFormData {

    private String subject;

    private String body;

    private List<String> receivers;

    private List<UploadedFile> attachments;

    public String getSubject() {
        return subject;
    }

    public void setSubject(final String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(final String body) {
        this.body = body;
    }

    public List<String> getReceivers() {
        return receivers;
    }

    public void setReceivers(final List<String> receivers) {
        this.receivers = receivers;
    }

    public List<UploadedFile> getAttachments() {
        return attachments;
    }

    public void setAttachments(final List<UploadedFile> attachments) {
        this.attachments = attachments;
    }
}
