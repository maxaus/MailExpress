package com.noveogroup.mailexpress.controller;

import com.noveogroup.mailexpress.BaseMockitoTest;
import com.noveogroup.mailexpress.dto.table.MessageListData;
import com.noveogroup.mailexpress.service.FolderService;
import com.noveogroup.mailexpress.service.MessageService;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Base test class for UI controllers.
 *
 * @author Maxim Baev
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({FacesContext.class, ResourceBundle.class})
public class AbstractUIControllerTest extends BaseMockitoTest {

    protected static final String BUNDLE_NAME = "MailExpress";

    @Mock
    protected MessageService messageService;

    @Mock
    protected FolderService folderService;

    @Mock
    protected MessageListData messageListData;

    protected Map<String, String> fcRequestMap = new HashMap<>();

    @Before
    public void mockFacesContext() {
        PowerMockito.mockStatic(FacesContext.class);
        PowerMockito.mockStatic(ResourceBundle.class);
        final FacesContext facesContext = mock(FacesContext.class);
        final ResourceBundle resourceBundle = mock(ResourceBundle.class);
        final ExternalContext context = mock(ExternalContext.class);
        final UIViewRoot viewRoot = mock(UIViewRoot.class);
        final Locale locale = new Locale("en");
        when(FacesContext.getCurrentInstance()).thenReturn(facesContext);
        when(facesContext.getExternalContext()).thenReturn(context);
        when(context.getRequestParameterMap()).thenReturn(fcRequestMap);
        when(viewRoot.getLocale()).thenReturn(locale);
        when(facesContext.getViewRoot()).thenReturn(viewRoot);
        when(ResourceBundle.getBundle(BUNDLE_NAME, locale)).thenReturn(resourceBundle);
    }
}
