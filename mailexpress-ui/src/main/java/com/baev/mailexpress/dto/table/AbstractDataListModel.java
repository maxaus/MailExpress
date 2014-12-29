package com.baev.mailexpress.dto.table;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.ajax4jsf.model.DataVisitor;
import org.ajax4jsf.model.ExtendedDataModel;
import org.ajax4jsf.model.Range;
import org.ajax4jsf.model.SequenceRange;
import org.richfaces.component.UIExtendedDataTable;
import org.richfaces.model.Arrangeable;

/**
 * Base class for data to be represented in UI data table.
 *
 * @param <T> entity class
 * @param <U> entity index class
 * @author Maxim Baev
 */
public abstract class AbstractDataListModel<T, U> extends ExtendedDataModel<T> implements Arrangeable {

    /**
     * Current row primary key.
     */
    protected U currentPk;

    /**
     * Row index.
     */
    protected int rowIndex;

    /**
     * Is descending order.
     */
    protected boolean descending = true;

    /**
     * Sort field.
     */
    protected String sortField;

    /**
     * Filter map.
     */
    protected Map<String, Object> filterMap = new HashMap<>();

    /**
     * Is in detached state.
     */
    protected boolean detached;

    /**
     * Wrapped keys.
     */
    protected List<U> wrappedKeys = new ArrayList<>();

    /**
     * Wrapped data.
     */
    protected Map<U, T> wrappedData = new HashMap<>();

    /**
     * Selected data items.
     */
    protected List<T> selectedItems = new ArrayList<>();

    /**
     * Selected data indexes.
     */
    protected Collection<U> selectedIndexes;

    /**
     * Listens to data table row selection event.
     * @param event event
     */
    public void selectionListener(final AjaxBehaviorEvent event) {
        final UIExtendedDataTable dataTable = (UIExtendedDataTable) event.getComponent();
        final Object originalKey = dataTable.getRowKey();
        selectedItems.clear();
        for (final Object selectionKey : selectedIndexes) {
            dataTable.setRowKey(selectionKey);
            if (dataTable.isRowAvailable()) {
                selectedItems.add((T) dataTable.getRowData());
            }
        }
        dataTable.setRowKey(originalKey);
    }

    /**
     * Gets selected indexes.
     *
     * @return the selected indexes
     */
    public Collection<U> getSelectedIndexes() {
        return selectedIndexes;
    }

    /**
     * Sets selected indexes.
     *
     * @param selectedIndexes the selected indexes
     */
    public void setSelectedIndexes(final Collection<U> selectedIndexes) {
        this.selectedIndexes = selectedIndexes;
    }

    /**
     * Gets selected item.
     *
     * @return the selected item
     */
    public T getSelectedItem() {
        if (selectedItems == null || selectedItems.isEmpty()) {
            return null;
        }
        return selectedItems.get(0);
    }

    /**
     * Sets selected items.
     *
     * @param selectedItems the selected items
     */
    public void setSelectedItems(final List<T> selectedItems) {
        this.selectedItems = selectedItems;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getRowKey() {
        return currentPk;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setRowKey(final Object key) {
        this.currentPk = (U) key;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setRowIndex(final int arg0) {
        rowIndex = arg0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setWrappedData(final Object data) {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getRowIndex() {
        return rowIndex;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getWrappedData() {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void walk(final FacesContext context, final DataVisitor visitor, final Range range, final Object argument) {
        final int firstRow = ((SequenceRange) range).getFirstRow();
        final int numberOfRows = ((SequenceRange) range).getRows();
        if (detached) {
            for (final U key : wrappedKeys) {
                setRowKey(key);
                visitor.process(context, key, argument);
            }
        } else {
            wrappedKeys = new ArrayList<>();
            for (final T object : findObjects(firstRow, numberOfRows, sortField, filterMap, descending)) {
                wrappedKeys.add(getId(object));
                wrappedData.put(getId(object), object);
                visitor.process(context, getId(object), argument);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isRowAvailable() {
        return currentPk != null && (wrappedKeys.contains(currentPk) || wrappedData.keySet().contains(currentPk)
                || getObjectById(currentPk) != null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T getRowData() {
        if (currentPk == null) {
            return null;
        }
        T object = wrappedData.get(currentPk);
        if (object == null) {
            object = getObjectById(currentPk);
            wrappedData.put(currentPk, object);
        }
        return object;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getRowCount() {
        return getNumRecords(filterMap).intValue();
    }

    /**
     * Returns entity ID.
     *
     * @param object entity
     * @return U entity index
     */
    public abstract U getId(T object);

    /**
     * Returns object for data table page.
     *
     * @param firstRow     first row
     * @param numberOfRows number of rows
     * @param sortField    sorting field name
     * @param descending   is sort direction is descending
     * @return List<T> List of data items
     */
    public abstract List<T> findObjects(int firstRow, int numberOfRows, String sortField, Map<String,
            Object> filterMap, boolean descending);

    /**
     * Returns entity by ID.
     *
     * @param id ID
     * @return T entity
     */
    public abstract T getObjectById(U id);

    /**
     * Returns number of records.
     *
     * @return Long Number of records
     */
    public abstract Long getNumRecords(Map<String, Object> filterMap);

}
