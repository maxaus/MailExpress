package com.noveogroup.mailexpress;

import org.junit.Before;

import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Base class for all unit tests using Mockito.
 *
 * @author Maxim Baev
 */
public abstract class BaseMockitoTest {

    /**
     * Initialize.
     */
    @Before
    public void setup() {
        initMocks(this);
    }
}
