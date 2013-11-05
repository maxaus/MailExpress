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

    String value = null;

    public String getValue() {
        return value;
    }

    @Override
    public String getFamily() {
        return COMPONENT_TYPE;
    }

    @Override
    public void encodeBegin(FacesContext context) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        writer.startElement("attachment", this);
        writer.write(getValue());
        writer.endElement("attachment");
    }


    @Override
    public void encodeEnd(FacesContext arg0) throws IOException {
        super.encodeEnd(arg0);
    }

    public void setValue(String value) {
        this.value = value;
    }
}
