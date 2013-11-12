package com.noveogroup.mailexpress;

import org.junit.Before;

import static org.mockito.MockitoAnnotations.initMocks;

/**
 * @author Maxim Baev
 */
public abstract class BaseMockitoTest {

    @Before
    public void setup() {
        initMocks(this);
    }
}
