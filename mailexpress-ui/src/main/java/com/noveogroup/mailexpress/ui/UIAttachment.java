package com.noveogroup.mailexpress.ui;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import java.io.IOException;

/**
 * @author Maxim Baev
 */
@FacesComponent("com.noveogroup.mailexpress.ui")
public class UIAttachment extends UIComponentBase {

    public static final String COMPONENT_TYPE = "com.noveogroup.mailexpress.ui";
    public static final String COMPONENT_TAG = "attachment";

    private String value = "";

    @Override
    public String getFamily() {
        return COMPONENT_TYPE;
    }

    @Override
    public void encodeBegin(final FacesContext context) throws IOException {
        final ResponseWriter writer = context.getResponseWriter();
        writer.startElement(COMPONENT_TAG, this);
        writer.write(getValue());
        writer.endElement(COMPONENT_TAG);
    }


    @Override
    public void encodeEnd(final FacesContext arg0) throws IOException {
        super.encodeEnd(arg0);
    }

    public String getValue() {
        return value;
    }

    public void setValue(final String value) {
        this.value = value;
    }
}
