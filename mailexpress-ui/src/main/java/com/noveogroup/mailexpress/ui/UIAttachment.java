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
    public static final String DIV_TAG = "div";

    private String value = "";

    @Override
    public String getFamily() {
        return COMPONENT_TYPE;
    }

    @Override
    public void encodeBegin(final FacesContext context) throws IOException {
        final ResponseWriter writer = context.getResponseWriter();
        //TODO:
        writer.startElement(DIV_TAG, this);
        writer.startElement("img", this);
        writer.writeAttribute("src", "fsdf.png", null);
        writer.writeAttribute("alt", "fsdf", null);
        writer.endElement("img");
        writer.write(getValue());
        writer.endElement(DIV_TAG);
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
