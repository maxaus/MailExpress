package com.noveogroup.mailexpress.dto.table;

import java.util.*;

import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.ajax4jsf.model.DataVisitor;
import org.ajax4jsf.model.ExtendedDataModel;
import org.ajax4jsf.model.Range;
import org.ajax4jsf.model.SequenceRange;
import org.richfaces.component.UIExtendedDataTable;
import org.richfaces.model.Arrangeable;

public abstract class PaginatingDataModel<T, U> extends ExtendedDataModel<T> implements Arrangeable {

    protected U currentPk;

    protected int rowIndex;

    protected boolean descending = true;

    protected String sortField = null;

    protected HashMap<String, Object> filterMap = new HashMap<>();

    protected boolean detached = false;

    protected List<U> wrappedKeys = new ArrayList<>();

    protected Map<U, T> wrappedData = new HashMap<>();

    protected List<T> selectedItems = new ArrayList<>();

    protected Collection<U> selectedIndexes;

    /**
     * @see org.ajax4jsf.model.ExtendedDataModel#getRowKey()
     */
    @Override
    public Object getRowKey() {
        return currentPk;
    }

    public void selectionListener(AjaxBehaviorEvent event) {
        UIExtendedDataTable dataTable = (UIExtendedDataTable) event.getComponent();
        Object originalKey = dataTable.getRowKey();
        selectedItems.clear();
        for (Object selectionKey : selectedIndexes) {
            dataTable.setRowKey(selectionKey);
            if (dataTable.isRowAvailable()) {
                selectedItems.add((T) dataTable.getRowData());
            }
        }
        dataTable.setRowKey(originalKey);
    }

    /**
     * @see org.ajax4jsf.model.ExtendedDataModel#setRowKey(java.lang.Object)
     */
    @SuppressWarnings("unchecked")
    @Override
    public void setRowKey(final Object key) {
        this.currentPk = (U) key;
    }

    /**
     * @see javax.faces.model.DataModel#setRowIndex(int)
     */
    @Override
    public void setRowIndex(final int arg0) {
        rowIndex = arg0;
    }

    /**
     * @see javax.faces.model.DataModel#setWrappedData(java.lang.Object)
     */
    @Override
    public void setWrappedData(final Object data) {
        throw new UnsupportedOperationException();
    }

    /**
     * @see javax.faces.model.DataModel#getRowIndex()
     */
    @Override
    public int getRowIndex() {
        return rowIndex;
    }

    /**
     * @see javax.faces.model.DataModel#getWrappedData()
     */
    @Override
    public Object getWrappedData() {
        throw new UnsupportedOperationException();
    }

    /**
     * @see org.ajax4jsf.model.ExtendedDataModel#walk(javax.faces.context.FacesContext, org.ajax4jsf.model.DataVisitor,
     *      org.ajax4jsf.model.Range, java.lang.Object)
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
        } else { // if not serialized, than we request data from data provider
            wrappedKeys = new ArrayList<>();
            for (final T object : findObjects(firstRow, numberOfRows, sortField, filterMap, descending)) {
                wrappedKeys.add(getId(object));
                wrappedData.put(getId(object), object);
                visitor.process(context, getId(object), argument);
            }
        }
    }

    /**
     * @see javax.faces.model.DataModel#isRowAvailable()
     */

    @Override
    public boolean isRowAvailable() {
        return currentPk != null && (wrappedKeys.contains(currentPk) || wrappedData.entrySet().contains(currentPk) || getObjectById(currentPk) != null);
    }

    /**
     * @see javax.faces.model.DataModel#getRowData()
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
     * @see javax.faces.model.DataModel#getRowCount()
     */
    @Override
    public int getRowCount() {
        return getNumRecords(filterMap).intValue();
    }

    public Collection<U> getSelectedIndexes() {
        return selectedIndexes;
    }

    public void setSelectedIndexes(Collection<U> selectedIndexes) {
        this.selectedIndexes = selectedIndexes;
    }

    public T getSelectedItem() {
        if (selectedItems == null || selectedItems.isEmpty()) {
            return null;
        }
        return selectedItems.get(0);
    }

    public List<T> getSelectedItems() {
        return selectedItems;
    }

    public void setSelectedItems(List<T> selectedItems) {
        this.selectedItems = selectedItems;
    }

    /**
     * @param object
     * @return U
     */
    public abstract U getId(T object);

    /**
     * @param firstRow
     * @param numberOfRows
     * @param sortField
     * @param descending
     * @return List<T>
     */
    public abstract List<T> findObjects(int firstRow, int numberOfRows, String sortField, HashMap<String, Object> filterMap, boolean descending);

    /**
     * @param id
     * @return T
     */
    public abstract T getObjectById(U id);

    /**
     * @return Long
     */
    public abstract Long getNumRecords(HashMap<String, Object> filterMap);

}
