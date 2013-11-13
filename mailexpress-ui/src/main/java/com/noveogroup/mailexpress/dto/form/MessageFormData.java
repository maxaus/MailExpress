package com.noveogroup.mailexpress.dto.form;

import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.io.Serializable;
import java.util.List;

/**
 * @author Maxim Baev
 */
@Component
@ManagedBean
@RequestScoped
public class MessageFormData implements Serializable {

    private static final long serialVersionUID = -2081260075515423870L;

    private Long id;

    private String subject;

    private String body;

    private List<String> receivers;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

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
}
