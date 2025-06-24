package main.java.observer;

import java.util.logging.Logger;
import java.util.logging.Level;

public class LoggingEventListener implements LibraryEventListener {
    private static final Logger logger = Logger.getLogger(LoggingEventListener.class.getName());
    
    @Override
    public void onLibraryEvent(LibraryEvent event) {
        logger.log(Level.INFO, event.toString());
    }
}