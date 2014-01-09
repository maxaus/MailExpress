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

    @Override
    public String getFamily() {
        return COMPONENT_TYPE;
    }

    @Override
    public void encodeBegin(final FacesContext context) throws IOException {
        final ResponseWriter writer = context.getResponseWriter();
        //TODO:
        String value = (String) getAttributes().get("value");
        writer.startElement(DIV_TAG, this);
        writer.startElement("img", this);
        writer.writeAttribute("src", "fsdf.png", null);
        writer.writeAttribute("alt", "fsdf", null);
        writer.endElement("img");
        writer.write(value);
        writer.endElement(DIV_TAG);
    }


    @Override
    public void encodeEnd(final FacesContext arg0) throws IOException {
        super.encodeEnd(arg0);
    }
}
