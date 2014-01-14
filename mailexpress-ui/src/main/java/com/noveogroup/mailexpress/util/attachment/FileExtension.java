package com.noveogroup.mailexpress.util.attachment;

/**
 * File extensions.
 *
 * @author Maxim Baev
 */
public enum FileExtension {

    /**
     * Zip file.
     */
    ZIP("zip"),
    /**
     * Rar file.
     */
    RAR("rar"),
    /**
     * 7z file.
     */
    SEVEN_ZIP("7z"),
    /**
     * Xls file.
     */
    XLS("xls"),
    /**
     * Xlsx file.
     */
    XLSX("xlsx"),
    /**
     * Doc file.
     */
    DOC("doc"),
    /**
     * Docx file.
     */
    DOCX("docx"),
    /**
     * Gif file.
     */
    GIF("gif"),
    /**
     * Jpg file.
     */
    JPG("jpg"),
    /**
     * Jpeg file.
     */
    JPEG("jpeg"),
    /**
     * Pdf file.
     */
    PDF("pdf"),
    /**
     * Png file.
     */
    PNG("png"),
    /**
     * ppt file.
     */
    PPT("ppt"),
    /**
     * Pptx file.
     */
    PPTX("pptx"),
    /**
     * Txt file.
     */
    TXT("txt"),
    /**
     * Rtf file.
     */
    RTF("rtf");


    /**
     * Enum value.
     */
    private final String value;

    /**
     * Private constructor.
     *
     * @param value enum value
     */
    private FileExtension(final String value) {
        this.value = value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return this.value;
    }
}
