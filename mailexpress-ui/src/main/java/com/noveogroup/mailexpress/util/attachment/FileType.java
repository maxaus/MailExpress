package com.noveogroup.mailexpress.util.attachment;

/**
 * File types (e.g. "word" is type for *.doc and *.docx files)
 *
 * @author Maxim Baev
 */
public enum FileType {

    /**
     * Archive file type.
     */
    ARCHIVE("archive"),
    /**
     * Excel file type.
     */
    EXCEL("excel"),
    /**
     * Word file type.
     */
    WORD("word"),
    /**
     * Gif file type.
     */
    GIF("gif"),
    /**
     * Jpeg file type.
     */
    JPEG("jpeg"),
    /**
     * Pdf file type.
     */
    PDF("pdf"),
    /**
     * Png file type.
     */
    PNG("png"),
    /**
     * Ppt file type.
     */
    PPT("ppt"),
    /**
     * Txt file type.
     */
    TXT("txt");

    /**
     * Enum value.
     */
    private final String value;

    /**
     * Private constructor.
     *
     * @param value enum value
     */
    private FileType(final String value) {
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
