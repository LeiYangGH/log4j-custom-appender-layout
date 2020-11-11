package assign251_2;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;

public class MemAppender extends AppenderSkeleton {
    private static MemAppender uniqueInstance;

    private MemAppender() {
    }

    public static MemAppender getInstance() {
        if (uniqueInstance == null) {
            synchronized (MemAppender.class) {
                // check again to avoid multi-thread access
                if (uniqueInstance == null)
                    uniqueInstance = new MemAppender();
            }
        }
        return uniqueInstance;
    }

    @Override
    protected void append(LoggingEvent loggingEvent) {

    }

    @Override
    public void close() {

    }

    @Override
    public boolean requiresLayout() {
        return false;
    }
}
