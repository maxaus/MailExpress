package com.noveogroup.mailexpress.util.converter;

import org.apache.commons.lang.StringUtils;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Custom converter to transform input string value to the list of strings.
 *
 * @author Maxim Baev
 */
@FacesConverter(value = "stringListConverter")
public class StringListConverter implements Converter {

    private static final String DELIMITER = ";";

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getAsObject(final FacesContext context, final UIComponent component, final String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }

        try {
            return Arrays.asList(value.replaceAll(" ", "").split(DELIMITER));
        } catch (Exception e) {
            e.printStackTrace();
            throw new ConverterException(new FacesMessage(String.format("Cannot convert %s to list", value)), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getAsString(final FacesContext context, final UIComponent component, final Object value) {
        if (value == null || !(value instanceof List)) {
            return null;
        }

        return StringUtils.join((List<String>) value, DELIMITER + " ");
    }
}
