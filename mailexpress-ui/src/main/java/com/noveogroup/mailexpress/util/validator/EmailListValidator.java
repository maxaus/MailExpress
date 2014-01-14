package com.noveogroup.mailexpress.util.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Custom validator that checks all entered email for matching email address pattern.
 *
 * @author Maxim Baev
 */
@FacesValidator("emailListValidator")
public class EmailListValidator implements Validator {

    private static final String BUNDLE_NAME = "MailExpress";
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\"
            + ".[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String VALIDATION_ERROR_KEY = "email_valid_msg";

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate(final FacesContext context, final UIComponent component, final Object value) {
        if (value != null) {
            checkEmailListString((List<String>) value, component, context.getViewRoot().getLocale());
        }
    }

    /**
     * Checks if any of the specified email addresses does not match email pattern. In such case throws {@link
     * ValidatorException}.
     *
     * @param emailList   list of entered emails
     * @param uiComponent input field
     * @param locale      current locale
     */
    private void checkEmailListString(final List<String> emailList, final UIComponent uiComponent,
                                      final Locale locale) {
        final Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        for (final String email : emailList) {
            final Matcher matcher = pattern.matcher(email);
            if (!matcher.matches()) {
                final ResourceBundle rb = ResourceBundle.getBundle(BUNDLE_NAME, locale);
                final String messageText = uiComponent.getAttributes().get("label") + ": " + rb.getString(
                        VALIDATION_ERROR_KEY);
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, messageText, messageText));
            }
        }
    }
}
