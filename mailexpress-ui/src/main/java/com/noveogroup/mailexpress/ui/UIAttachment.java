package com.noveogroup.mailexpress.ui;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Maxim Baev
 */
@FacesComponent("com.noveogroup.mailexpress.ui")
public class UIAttachment extends UIComponentBase {

    private static final String COMPONENT_TYPE = "com.noveogroup.mailexpress.ui";
    private static final String DIV_TAG = "div";
    private static final String IMG_TAG = "img";
    private static final String IMG_RESOURCES_PREFIX = "resources/img/file/";
    private static final String ICON_TYPE = ".png";

    private static final Map<String, String> ICON_MAP = new HashMap<>();

    public UIAttachment() {
        super();
        ICON_MAP.put("zip", "archive");
        ICON_MAP.put("rar", "archive");
        ICON_MAP.put("7z", "archive");
        ICON_MAP.put("xls", "excel");
        ICON_MAP.put("xlsx", "excel");
        ICON_MAP.put("doc", "word");
        ICON_MAP.put("docx", "word");
        ICON_MAP.put("gif", "gif");
        ICON_MAP.put("jpg", "jpeg");
        ICON_MAP.put("jpeg", "jpeg");
        ICON_MAP.put("pdf", "pdf");
        ICON_MAP.put("png", "png");
        ICON_MAP.put("ppt", "ppt");
        ICON_MAP.put("pptx", "ppt");
        ICON_MAP.put("txt", "txt");
        ICON_MAP.put("rtf","txt");
    }

    @Override
    public String getFamily() {
        return COMPONENT_TYPE;
    }

    @Override
    public void encodeBegin(final FacesContext context) throws IOException {
        final ResponseWriter writer = context.getResponseWriter();
        final String fileName = (String) getAttributes().get("value");
        writer.startElement(DIV_TAG, this);
        writer.startElement(IMG_TAG, this);
        writer.writeAttribute("src", getIconPath(fileName), null);
        writer.writeAttribute("alt", fileName, null);
        writer.writeAttribute("title", fileName, null);
        writer.endElement(IMG_TAG);
        writer.write(fileName);
        writer.endElement(DIV_TAG);
    }

    private String getIconPath(final String fileName) {
        final String fileExtension = FilenameUtils.getExtension(fileName);
        String iconName = ICON_MAP.get(fileExtension.toLowerCase());
        if (StringUtils.isEmpty(iconName)) {
            iconName = "file";
        }
        return IMG_RESOURCES_PREFIX + iconName + ICON_TYPE;
    }


    @Override
    public void encodeEnd(final FacesContext arg0) throws IOException {
        super.encodeEnd(arg0);
    }
}
