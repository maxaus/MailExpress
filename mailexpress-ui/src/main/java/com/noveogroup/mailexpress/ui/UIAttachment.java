package com.noveogroup.mailexpress.ui;

import com.noveogroup.mailexpress.util.attachment.FileExtension;
import com.noveogroup.mailexpress.util.attachment.FileType;
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
 * Custom tag to display attachment name with icon.
 *
 * @author Maxim Baev
 */
@FacesComponent("com.noveogroup.mailexpress.ui")
public class UIAttachment extends UIComponentBase {

    private static final String COMPONENT_TYPE = "com.noveogroup.mailexpress.ui";
    private static final String DIV_TAG = "div";
    private static final String IMG_TAG = "img";
    private static final String IMG_RESOURCES_PREFIX = "resources/img/file/";
    private static final String ICON_TYPE = ".png";

    private static final Map<FileExtension, FileType> ICON_MAP = new HashMap<>();

    public UIAttachment() {
        super();
        ICON_MAP.put(FileExtension.ZIP, FileType.ARCHIVE);
        ICON_MAP.put(FileExtension.RAR, FileType.ARCHIVE);
        ICON_MAP.put(FileExtension.SEVEN_ZIP, FileType.ARCHIVE);
        ICON_MAP.put(FileExtension.XLS, FileType.EXCEL);
        ICON_MAP.put(FileExtension.XLSX, FileType.EXCEL);
        ICON_MAP.put(FileExtension.DOC, FileType.WORD);
        ICON_MAP.put(FileExtension.DOCX, FileType.WORD);
        ICON_MAP.put(FileExtension.GIF, FileType.GIF);
        ICON_MAP.put(FileExtension.JPG, FileType.JPEG);
        ICON_MAP.put(FileExtension.JPEG, FileType.JPEG);
        ICON_MAP.put(FileExtension.PDF, FileType.PDF);
        ICON_MAP.put(FileExtension.PNG, FileType.PNG);
        ICON_MAP.put(FileExtension.PPT, FileType.PPT);
        ICON_MAP.put(FileExtension.PPTX, FileType.PPT);
        ICON_MAP.put(FileExtension.TXT, FileType.TXT);
        ICON_MAP.put(FileExtension.RTF, FileType.TXT);
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
        String iconName = ICON_MAP.get(FileExtension.valueOf(fileExtension.toUpperCase())).toString();
        if (StringUtils.isEmpty(iconName)) {
            iconName = "file";
        }
        return IMG_RESOURCES_PREFIX + iconName + ICON_TYPE;
    }
}
