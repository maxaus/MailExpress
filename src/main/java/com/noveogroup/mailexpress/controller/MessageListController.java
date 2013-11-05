package com.noveogroup.mailexpress.controller;

import org.richfaces.component.SortOrder;
import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Maxim Baev
 */
@SessionScoped
@ManagedBean
@Component
public class MessageListController {
    private static final String SORT_PROPERTY_PARAMETER = "sortProperty";
    private static final int DEFAULT_PAGE_SIZE = 10;

    private SortOrder subjectOrder = SortOrder.unsorted;
    private SortOrder senderOrder = SortOrder.unsorted;
    private SortOrder dateOrder = SortOrder.unsorted;

    private int pageNum = 0;
    private int pageSize = DEFAULT_PAGE_SIZE;

    private Map<String, SortOrder> sortsOrders;


    public MessageListController() {
        sortsOrders = new HashMap<>();
    }

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

    public void sort() {
        String property = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
                .get(SORT_PROPERTY_PARAMETER);
        if (property != null) {
            SortOrder currentPropertySortOrder = sortsOrders.get(property);
            sortsOrders.clear();
            if (currentPropertySortOrder == null || currentPropertySortOrder.equals(SortOrder.descending)) {
                sortsOrders.put(property, SortOrder.ascending);
            } else {
                sortsOrders.put(property, SortOrder.descending);
            }
        }
    }

    public Map<String, SortOrder> getSortsOrders() {
        return sortsOrders;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
