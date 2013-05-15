package gov.nysenate.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import gov.nysenate.util.listener.NYSenateConfigurationListener;

import java.util.Observable;
import java.util.Observer;

import org.junit.Before;
import org.junit.Test;

/** Test the Config implementation using the test.app.properties resource file. *
 * @see Config */

public class ConfigTest
{
    private NYSenateConfigurationListener listener;
    private Config config;

    private static class DummyConfigConsumer implements Observer
    {
        boolean isUpdated = false;
        Observable observable;

        @Override
        public void update(Observable o, Object arg)
        {
            isUpdated = true;
            observable = o;
        }
    }

    @Before
    public void setUp() throws Exception
    {
        config = new Config("test.app.properties");
    }

    @Test
    public void configReturnsCorrectValuesForValidKeys() throws Exception
    {
        /** Check common keys that should not change any time soon */
        assertEquals("simple_value", config.getValue("simple.key"));
        assertEquals("spaces  value", config.getValue("spaces.key"));
    }

    @Test
    public void configReturnsCorrectValueForVariableKeys() throws Exception
    {
        assertEquals("simple_value variable", config.getValue("variable.key"));
        assertEquals("simple_value - simple_value variable", config.getValue("repeated.variable.key"));
        assertEquals("\"one\" two two 'three'", config.getValue("nested.keys"));
    }

    @Test
    public void configReturnsEmptyStringOnInvalidKey()
    {
        assertEquals("", config.getValue("bad key"));
        assertEquals("", config.getValue("unknown.variable.key"));
    }

    @Test
    public void configNotifiesObserversProperly() throws Exception
    {
        DummyConfigConsumer d1 = new DummyConfigConsumer();
        DummyConfigConsumer d2 = new DummyConfigConsumer();

        assertFalse(d1.isUpdated);
        assertFalse(d2.isUpdated);

        config.notifyOnChange(d1);
        assertEquals(1, listener.countObservers());

        config.notifyOnChange(d2);
        assertEquals(2, listener.countObservers());

        /** Trigger an update on the properties file using a variable value
         * which sets the key value again. */
        config.getValue("variable.key");

        /** Check that update was called on observers */
        assertTrue(d1.isUpdated);
        assertTrue(d2.isUpdated);

        /** And that the Observable type was correct */
        assertEquals(NYSenateConfigurationListener.class, d1.observable.getClass());
        assertEquals(NYSenateConfigurationListener.class, d2.observable.getClass());
    }
}
