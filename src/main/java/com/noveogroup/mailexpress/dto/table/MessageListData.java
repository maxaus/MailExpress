package com.noveogroup.mailexpress.dto.table;

import com.noveogroup.mailexpress.controller.FolderController;
import com.noveogroup.mailexpress.dto.FolderNode;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Maxim Baev
 */
@Component
@ManagedBean
@RequestScoped
public class MessageListData extends PaginatingDataModel<Message, Long> {

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
    public Long getId(Message message) {
        return message.getId();
    }

    @Override
    public List<Message> findObjects(int firstRow, int numberOfRows, String sortField, HashMap<String, Object> filterMap, boolean descending) {
        FolderNode folderNode = folderController.getCurrentSelection();
        Long folderId = null;
        if (folderNode != null) {
            folderId = folderNode.getId();
        }
        return messageService.find(folderId, sortField, descending ? "desc" : "asc", firstRow / numberOfRows, numberOfRows);
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
        if (state != null) {
            List<SortField> sortFields = state.getSortFields();
            if (sortFields != null && !sortFields.isEmpty()) {

                SortField field = sortFields.get(0);
                ValueExpression sortBy = field.getSortBy();
                 String exp = sortBy.getExpressionString();
                this.sortField = exp.substring(exp.lastIndexOf('.')+1, exp.lastIndexOf('}'));
                this.descending = SortOrder.descending == field.getSortOrder();
            }
        }
    }
}
