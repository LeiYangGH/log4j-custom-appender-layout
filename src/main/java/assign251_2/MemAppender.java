package assign251_2;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class MemAppender extends AppenderSkeleton {

//    private static MemAppender uniqueInstance;
    private CopyOnWriteArrayList<LoggingEvent> lstEvents = new CopyOnWriteArrayList<>();

//    private MemAppender() {
//    }
//
//    public static MemAppender getInstance() {
//        if (uniqueInstance == null) {
//            synchronized (MemAppender.class) {
//                // check again to avoid multi-thread access
//                if (uniqueInstance == null)
//                    uniqueInstance = new MemAppender();
//            }
//        }
//        return uniqueInstance;
//    }

    @Override
    protected void append(LoggingEvent loggingEvent) {
        this.lstEvents.add(loggingEvent);
    }

    @Override
    public void close() {

    }

    @Override
    public boolean requiresLayout() {
        return true;
    }

    public List<LoggingEvent> getCurrentLogs() {
        return Collections.unmodifiableList(this.lstEvents);
    }

    public List<String> getEventStrings() {
        return Collections.unmodifiableList(this.getCurrentLogs().stream().map(x ->
                x.getRenderedMessage()).collect(Collectors.toList()));
    }

    public void printLogs() {
        for (String s : getEventStrings()) {
            System.out.println(s);
        }
        this.lstEvents.clear();
    }

}
