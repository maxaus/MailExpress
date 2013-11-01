package com.noveogroup.mailexpress.ui;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import java.io.IOException;

/**
 * @author Maxim Baev
 */
@FacesComponent("com.noveogroup.mailexpress.ui")
public class UIAttachment extends UIComponentBase {

    @Override
    public String getFamily() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void encodeBegin(FacesContext context) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        writer.startElement("div", this);
    }

    public void encodeEnd(FacesContext context) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        writer.endElement("div");
    }
}
