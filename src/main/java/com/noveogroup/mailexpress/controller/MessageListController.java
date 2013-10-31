package com.noveogroup.mailexpress.controller;

import org.richfaces.component.SortOrder;
import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * @author Maxim Baev
 */
@SessionScoped
@ManagedBean
@Component
public class MessageListController {

    private SortOrder subjectOrder = SortOrder.unsorted;
    private SortOrder senderOrder = SortOrder.unsorted;
    private SortOrder dateOrder = SortOrder.unsorted;

    public void sortBySubject() {
        senderOrder = SortOrder.unsorted;
        dateOrder = SortOrder.unsorted;
        if (subjectOrder.equals(SortOrder.ascending)) {
            setSubjectOrder(SortOrder.descending);
        } else {
            setSubjectOrder(SortOrder.ascending);
        }
    }

    public void sortBySender() {
        subjectOrder = SortOrder.unsorted;
        dateOrder = SortOrder.unsorted;
        if (senderOrder.equals(SortOrder.ascending)) {
            setSenderOrder(SortOrder.descending);
        } else {
            setSenderOrder(SortOrder.ascending);
        }
    }

    public void sortByDate() {
        senderOrder = SortOrder.unsorted;
        subjectOrder = SortOrder.unsorted;
        if (dateOrder.equals(SortOrder.ascending)) {
            setDateOrder(SortOrder.descending);
        } else {
            setDateOrder(SortOrder.ascending);
        }
    }

    public SortOrder getSubjectOrder() {
        return subjectOrder;
    }

    public void setSubjectOrder(SortOrder subjectOrder) {
        this.subjectOrder = subjectOrder;
    }

    public SortOrder getSenderOrder() {
        return senderOrder;
    }

    public void setSenderOrder(SortOrder senderOrder) {
        this.senderOrder = senderOrder;
    }

    public SortOrder getDateOrder() {
        return dateOrder;
    }

    public void setDateOrder(SortOrder dateOrder) {
        this.dateOrder = dateOrder;
    }
}
