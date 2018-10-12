package gov.nysenate.util.listener;

import java.util.Observable;

import org.apache.commons.configuration.event.ConfigurationErrorEvent;
import org.apache.commons.configuration.event.ConfigurationErrorListener;
import org.apache.commons.configuration.event.ConfigurationEvent;
import org.apache.commons.configuration.event.ConfigurationListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * NYSenateConfigurationListener listens to any changes to property files that have been loaded with
 * the {@link org.apache.commons.configuration.PropertiesConfiguration} class.
 */
public class NYSenateConfigurationListener extends Observable
             implements ConfigurationListener, ConfigurationErrorListener
{
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    public NYSenateConfigurationListener() {}

    @Override
    public void configurationChanged(ConfigurationEvent configurationEvent)
    {
        if (!configurationEvent.isBeforeUpdate())
        {
            logger.info(String.format("Configuration updated - notifying %d observers", this.countObservers()));
            setChanged();
            notifyObservers(this.getClass());
        }
    }

    @Override
    public void configurationError(ConfigurationErrorEvent configurationErrorEvent)
    {
        logger.error("A configuration error occurred!");
        logger.error(configurationErrorEvent.getCause().getMessage() + " " +
               configurationErrorEvent.getCause().getStackTrace());
    }
}
