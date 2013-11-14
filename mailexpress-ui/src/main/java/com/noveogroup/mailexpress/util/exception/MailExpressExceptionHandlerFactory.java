package com.noveogroup.mailexpress.util.exception;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

/**
 * Custom exception handler factory.
 *
 * @author Maxim Baev
 */
public class MailExpressExceptionHandlerFactory extends ExceptionHandlerFactory {
    private ExceptionHandlerFactory parent;

    /**
     * Constructor.
     *
     * @param parent parent exception handler factory
     */
    public MailExpressExceptionHandlerFactory(final ExceptionHandlerFactory parent) {
        this.parent = parent;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ExceptionHandler getExceptionHandler() {
        return new MailExpressExceptionHandler(parent.getExceptionHandler());
    }
}