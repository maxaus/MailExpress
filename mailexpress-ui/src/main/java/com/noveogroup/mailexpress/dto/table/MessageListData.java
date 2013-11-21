package com.noveogroup.mailexpress.dto.table;

import com.noveogroup.mailexpress.controller.FolderController;
import com.noveogroup.mailexpress.domain.Attachment;
import com.noveogroup.mailexpress.dto.AttachmentDto;
import com.noveogroup.mailexpress.dto.MessageDto;
import com.noveogroup.mailexpress.domain.Message;
import com.noveogroup.mailexpress.service.MessageService;
import org.richfaces.component.SortOrder;
import org.richfaces.model.ArrangeableState;
import org.richfaces.model.SortField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.el.ValueExpression;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.bean.ViewScoped;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * {@link AbstractDataListModel} implementation for the message list.
 *
 * @author Maxim Baev
 */
@Component
@ManagedBean
@ViewScoped
public class MessageListData extends AbstractDataListModel<MessageDto, Long> {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
    private static final int DEFAULT_PAGE_SIZE = 10;

    private int pageNum = 1;
    private int pageSize = DEFAULT_PAGE_SIZE;
    private Map<String, SortOrder> sortsOrders;

    @Autowired
    private MessageService messageService;

    @Autowired
    private FolderController folderController;

    /**
     * Instantiates a new Message list data.
     */
    public MessageListData() {
        sortsOrders = new HashMap<>();
    }

    /**
     * Gets sorts orders.
     *
     * @return the sorts orders
     */
    public Map<String, SortOrder> getSortsOrders() {
        return sortsOrders;
    }

    /**
     * Gets page num.
     *
     * @return the page num
     */
    public int getPageNum() {
        return pageNum;
    }

    /**
     * Sets page num.
     *
     * @param pageNum the page num
     */
    public void setPageNum(final int pageNum) {
        this.pageNum = pageNum;
    }

    /**
     * Gets page size.
     *
     * @return the page size
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * Sets page size.
     *
     * @param pageSize the page size
     */
    public void setPageSize(final int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long getId(final MessageDto message) {
        return message.getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MessageDto> findObjects(final int firstRow, final int numberOfRows, final String sortField,
                                        final Map<String, Object> filterMap, final boolean descending) {
        final Long folderId = folderController.getSelectedFolderId();
        if (folderId != null) {
            return createDtoList(messageService.findByFolder(folderId, sortField, descending ? "desc" : "asc",
                    firstRow / numberOfRows, numberOfRows));
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MessageDto getObjectById(final Long id) {
        return createDto(messageService.getById(id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long getNumRecords(final Map<String, Object> filterMap) {
        final Long folderId = folderController.getSelectedFolderId();
        if (folderId != null) {
            return messageService.countByFolder(folderId);
        } else {
            return 0L;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void arrange(final FacesContext context, final ArrangeableState state) {
        if (state != null) {
            final List<SortField> sortFields = state.getSortFields();
            if (sortFields != null && !sortFields.isEmpty()) {
                final SortField field = sortFields.get(0);
                final ValueExpression sortBy = field.getSortBy();
                final String exp = sortBy.getExpressionString();
                this.sortField = exp.substring(exp.lastIndexOf('.') + 1, exp.lastIndexOf('}'));
                this.descending = SortOrder.descending == field.getSortOrder();
            }
        }
    }

    private List<MessageDto> createDtoList(final List<Message> messages) {
        final List<MessageDto> dtoList = new ArrayList<>();
        for (final Message message : messages) {
            dtoList.add(createDto(message));
        }
        return dtoList;
    }

    private MessageDto createDto(final Message message) {
        if (message != null) {
            final MessageDto dto = new MessageDto();
            dto.setId(message.getId());
            dto.setSender(message.getSender().getEmail());
            dto.setSubject(message.getSubject());
            dto.setUnread(message.isUnread());
            dto.setBody(message.getBody());
            dto.setDate(DATE_FORMAT.format(message.getDate()));
            for (final Attachment attachment : message.getAttachments()) {
                dto.addAttachment(createAttachmentDto(attachment));
            }
            return dto;
        } else {
            return null;
        }
    }

    private AttachmentDto createAttachmentDto(final Attachment attachment) {
        final AttachmentDto dto = new AttachmentDto();
        dto.setId(attachment.getId());
        dto.setPath(attachment.getPath());
        return dto;
    }
}
