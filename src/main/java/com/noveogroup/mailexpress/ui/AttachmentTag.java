package com.noveogroup.mailexpress.ui;

import javax.faces.webapp.UIComponentELTag;
/**
 * @author Maxim Baev
 */
public class AttachmentTag extends UIComponentELTag {
    @Override
    public String getComponentType() {
        return "attachment";
    }

    @Override
    public String getRendererType() {
        return null;
    }
}
