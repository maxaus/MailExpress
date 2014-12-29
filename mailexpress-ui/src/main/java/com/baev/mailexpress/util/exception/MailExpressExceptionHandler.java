package com.baev.mailexpress.util.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/**
 * Custom application exception handler.
 *
 * @author Maxim Baev
 */
public class MailExpressExceptionHandler extends ExceptionHandlerWrapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(MailExpressExceptionHandler.class);
    private ExceptionHandler wrapped;

    /**
     * Constructor.
     *
     * @param wrapped exception handler
     */
    public MailExpressExceptionHandler(final ExceptionHandler wrapped) {
        this.wrapped = wrapped;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ExceptionHandler getWrapped() {
        return wrapped;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handle() {
        final Iterator iterator = getUnhandledExceptionQueuedEvents().iterator();
        while (iterator.hasNext()) {
            final ExceptionQueuedEvent event = (ExceptionQueuedEvent) iterator.next();
            final ExceptionQueuedEventContext eventContext =
                    (ExceptionQueuedEventContext) event.getSource();

            final ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            final Map<String, Object> requestMap = externalContext.getRequestMap();
            try {
                try {
                    final Throwable t = eventContext.getException();
                    requestMap.put("errorMsg", t.getMessage());
                    externalContext.dispatch("/error.xhtml");
                } catch (IOException e) {
                    LOGGER.error("Error view '/error.xhtml' not found", e);
                }
            } finally {
                iterator.remove();
            }
        }
        getWrapped().handle();
    }
}
