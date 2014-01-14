package com.noveogroup.mailexpress.util.converter;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import java.util.Arrays;
import java.util.List;

/**
 * Custom converter to transform input string value to the list of strings.
 *
 * @author Maxim Baev
 */
@FacesConverter(value = "stringListConverter")
public class StringListConverter implements Converter {

    private static final Logger LOGGER = LoggerFactory.getLogger(StringListConverter.class);

    private static final String DELIMITER = ";";
    private static final String WHITESPACE = " ";

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getAsObject(final FacesContext context, final UIComponent component, final String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }

        try {
            return Arrays.asList(value.replaceAll(WHITESPACE, StringUtils.EMPTY).split(DELIMITER));
        } catch (Exception e) {
            LOGGER.error("Failed to convert string to list", e);
            throw new ConverterException(new FacesMessage(String.format("Cannot convert %s to list", value)), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getAsString(final FacesContext context, final UIComponent component, final Object value) {
        if (!(value instanceof List)) {
            return null;
        }

        return StringUtils.join((List<String>) value, DELIMITER + WHITESPACE);
    }
}
