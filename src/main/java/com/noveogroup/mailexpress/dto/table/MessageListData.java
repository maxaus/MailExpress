package com.noveogroup.mailexpress.dto.table;

import com.noveogroup.mailexpress.controller.FolderController;
import com.noveogroup.mailexpress.dto.MessageItem;
import com.noveogroup.mailexpress.model.Message;
import com.noveogroup.mailexpress.service.MessageService;
import org.richfaces.component.SortOrder;
import org.richfaces.model.ArrangeableState;
import org.richfaces.model.SortField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.el.ValueExpression;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Maxim Baev
 */
@Component
@ManagedBean
@RequestScoped
public class MessageListData extends PaginatingDataModel<MessageItem, Long> {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");

    @Autowired
    private MessageService messageService;

    @Autowired
    private FolderController folderController;

    private static final int DEFAULT_PAGE_SIZE = 10;

    private int pageNum = 1;
    private int pageSize = DEFAULT_PAGE_SIZE;

    private Map<String, SortOrder> sortsOrders;

    public MessageListData() {
        sortsOrders = new HashMap<>();
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

    @Override
    public Long getId(MessageItem message) {
        return message.getId();
    }

    @Override
    public List<MessageItem> findObjects(int firstRow, int numberOfRows, String sortField, HashMap<String, Object> filterMap, boolean descending) {
        Long folderId = folderController.getSelectedFolderId();
        if (folderId != null) {
            return createDtoList(messageService.findByFolder(folderId, sortField, descending ? "desc" : "asc", firstRow / numberOfRows, numberOfRows));
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public MessageItem getObjectById(Long id) {
        return createDto(messageService.getMessageById(id));
    }

    @Override
    public Long getNumRecords(HashMap<String, Object> filterMap) {
        Long folderId = folderController.getSelectedFolderId();
        if (folderId != null) {
            return messageService.countByFolder(folderId);
        } else {
            return 0L;
        }
    }

    @Override
    public void arrange(FacesContext context, ArrangeableState state) {
        if (state != null) {
            List<SortField> sortFields = state.getSortFields();
            if (sortFields != null && !sortFields.isEmpty()) {

                SortField field = sortFields.get(0);
                ValueExpression sortBy = field.getSortBy();
                String exp = sortBy.getExpressionString();
                this.sortField = exp.substring(exp.lastIndexOf('.') + 1, exp.lastIndexOf('}'));
                this.descending = SortOrder.descending == field.getSortOrder();
            }
        }
    }

    private List<MessageItem> createDtoList(List<Message> messages) {
        List<MessageItem> dtoList = new ArrayList<>();
        for (Message message : messages) {
            dtoList.add(createDto(message));
        }
        return dtoList;
    }

    private MessageItem createDto(Message message) {
        MessageItem dto = new MessageItem();
        dto.setId(message.getId());
        dto.setSender(message.getSender().getEmail());
        dto.setSubject(message.getSubject());
        dto.setUnread(message.isUnread());
        dto.setBody(message.getBody());
//        dto.setWithAttachment(CollectionUtils.isNotEmpty(message.getAttachments()));
        dto.setDate(DATE_FORMAT.format(message.getDate()));
        return dto;
    }

    public void changeReadStatus() {
        String msgId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("msgId");
        Message message = messageService.getMessageById(Long.valueOf(msgId));
        message.setUnread(!message.isUnread());
        messageService.saveMessage(message);
    }
}
