package com.baev.mailexpress.util.attachment;

import org.apache.commons.io.FileUtils;
import org.richfaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Attachment utility class.
 *
 * @author Maxim Baev
 */
public final class AttachmentUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(AttachmentUtil.class);

    /**
     * Private constructor.
     */
    private AttachmentUtil() {
    }

    /**
     * Saves file uploaded as message attachment.
     *
     * @param uploadPath upload folder absolute path
     * @param item       uploaded file model object
     * @throws IOException error while saving file
     */
    public static void saveAttachment(final String uploadPath, final UploadedFile item) throws IOException {
        try {
            final File file = new File(uploadPath + item.getName());
            FileUtils.writeByteArrayToFile(file, item.getData());
        } catch (IOException e) {
            LOGGER.error("Problem while saving file.", e);
            throw e;
        }
    }

    /**
     * Writes attachment content to response.
     *
     * @param res        response
     * @param uploadPath upload folder absolute path
     * @param filename   attachment file name
     * @throws IOException error while uploading file
     */
    public static void writeAttachmentToResponse(final HttpServletResponse res, final String uploadPath,
                                                 final String filename) throws IOException {
        try {
            res.setHeader("Content-disposition", "attachment; filename=" + filename);
            final File file = new File(uploadPath + filename);
            final FileInputStream fis = new FileInputStream(file);
            final ServletOutputStream os = res.getOutputStream();
            int bt = fis.read();
            while (bt != -1) {
                os.write(bt);
                bt = fis.read();
            }
            os.flush();
            fis.close();
            os.close();
        } catch (IOException e) {
            LOGGER.error("Problem uploading file", e);
            throw e;
        }
    }
}
