package com.noveogroup.mailexpress.dto;

import com.noveogroup.mailexpress.model.Message;
import com.noveogroup.mailexpress.service.MessageService;
import org.richfaces.model.ArrangeableState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import java.util.HashMap;
import java.util.List;

/**
 * @author Maxim Baev
 */
@Component
@ManagedBean
@RequestScoped
public class MessagePaginatingDataModel extends PaginatingDataModel<Message, Long> {

    @Autowired
    private MessageService messageService;

    @Override
    public Long getId(Message message) {
        return message.getId();
    }

    @Override
    public List<Message> findObjects(int firstRow, int numberOfRows, String sortField, HashMap<String, Object> filterMap, boolean descending) {
        return messageService.find(sortField, descending ? "desc" : "asc", firstRow/numberOfRows, numberOfRows);
    }

    @Override
    public Message getObjectById(Long id) {
        return messageService.getMessageById(id);
    }

    @Override
    public Long getNumRecords(HashMap<String, Object> filterMap) {
        return messageService.getCount();
    }

    @Override
    public void arrange(FacesContext context, ArrangeableState state) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
