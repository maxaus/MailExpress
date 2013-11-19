package com.noveogroup.mailexpress.util.data;

/**
 * Indicates that class should contain method to clear data.
 *
 * @author Maxim Baev
 */
public interface Clearable {

    /**
     * Clears unnecessary values in data bean.
     */
    void clear();
}
