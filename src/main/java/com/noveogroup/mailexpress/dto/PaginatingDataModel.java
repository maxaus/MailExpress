package com.noveogroup.mailexpress.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.ajax4jsf.model.DataVisitor;
import org.ajax4jsf.model.ExtendedDataModel;
import org.ajax4jsf.model.Range;
import org.ajax4jsf.model.SequenceRange;
import org.richfaces.model.Arrangeable;

public abstract class PaginatingDataModel<T, U> extends ExtendedDataModel<T> implements Arrangeable {

    protected U currentPk;

    protected int rowIndex;

    protected boolean descending = true;

    protected String sortField = null;

    protected HashMap<String,Object> filterMap = new HashMap<>();

    protected boolean detached = false;

    protected List<U> wrappedKeys = new ArrayList<>();

    protected Integer rowCount;

    protected Map<U, T> wrappedData = new HashMap<>();

    /**
     *
     * @see org.ajax4jsf.model.ExtendedDataModel#getRowKey()
     */
    @Override
    public Object getRowKey() {
        return currentPk;
    }

    /**
     *
     * @see org.ajax4jsf.model.ExtendedDataModel#setRowKey(java.lang.Object)
     */

    @SuppressWarnings("unchecked")
    @Override
    public void setRowKey(final Object key) {
        this.currentPk = (U) key;
    }

    /**
     *
     * @see javax.faces.model.DataModel#setRowIndex(int)
     */
    @Override
    public void setRowIndex(final int arg0) {
        rowIndex = arg0;
    }

    /**
     *
     * @see javax.faces.model.DataModel#setWrappedData(java.lang.Object)
     */
    @Override
    public void setWrappedData(final Object data) {
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @see javax.faces.model.DataModel#getRowIndex()
     */
    @Override
    public int getRowIndex() {
        return rowIndex;
    }

    /**
     *
     * @see javax.faces.model.DataModel#getWrappedData()
     */
    @Override
    public Object getWrappedData() {
        throw new UnsupportedOperationException();
    }

    /**
     *
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
     *
     * @see javax.faces.model.DataModel#isRowAvailable()
     */

    @Override
    public boolean isRowAvailable() {
        if (currentPk == null) {
            return false;
        }
        if (wrappedKeys.contains(currentPk)) {
            return true;
        }
        if (wrappedData.entrySet().contains(currentPk)) {
            return true;
        }
        try {
            if (getObjectById(currentPk) != null) {
                return true;
            }
        } catch (final Exception e) {
        }
        return false;
    }

    /**
     *
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
     *
     * @see javax.faces.model.DataModel#getRowCount()
     */
    @Override
    public int getRowCount() {
        if (rowCount == null) {
            rowCount = getNumRecords(filterMap).intValue();
        }
        return rowCount;
    }

    /**
     * @param object
     *
     * @return U
     */
    public abstract U getId(T object);

    /**
     *
     * @param firstRow
     *
     * @param numberOfRows
     *
     * @param sortField
     *
     * @param descending
     *
     * @return List<T>
     */
    public abstract List<T> findObjects(int firstRow, int numberOfRows, String sortField, HashMap<String,Object> filterMap, boolean descending);

    /**
     *
     * @param id
     *
     * @return T
     */
    public abstract T getObjectById(U id);

    /**
     *
     * @return Long
     */
    public abstract Long getNumRecords(HashMap<String,Object> filterMap);

}
