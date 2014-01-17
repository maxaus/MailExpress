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

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import java.util.HashMap;
import java.util.Map;

import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Base test class for UI controllers.
 *
 * @author Maxim Baev
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({FacesContext.class})
public class AbstractUIControllerTest extends BaseMockitoTest {

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
        final FacesContext mockFacesContext = mock(FacesContext.class);
        final ExternalContext context = mock(ExternalContext.class);
        when(FacesContext.getCurrentInstance()).thenReturn(mockFacesContext);
        when(mockFacesContext.getExternalContext()).thenReturn(context);
        when(context.getRequestParameterMap()).thenReturn(fcRequestMap);
    }
}
