package assign251_2;

import static org.junit.Assert.assertEquals;

import javax.naming.ConfigurationException;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.PatternLayout;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class StressTests {

    MemAppender appender = MemAppender.getInstance();

    @Before
    public void initEachAppenderTest()
            throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Helpers.initEachAppender();
    }

    @Test
    public void add90000LogsTest() throws ConfigurationException {
        appender.setLayout(new VelocityLayout());
        for (int i = 0; i < 90000; i++) {
            Helpers.addSampleLoggingEvent(appender);
        }
        assertEquals(10000, appender.getCurrentLogs().size());
        assertEquals(80000, appender.getDiscardedLogCount());
    }

    @Test
    public void setMaxSizeThenAdd90000LogsTest() throws ConfigurationException {
        appender.setMaxSize(100000);
        appender.setLayout(new VelocityLayout());
        for (int i = 0; i < 90000; i++) {
            Helpers.addSampleLoggingEvent(appender);
        }
        assertEquals(90000, appender.getCurrentLogs().size());
        assertEquals(0, appender.getDiscardedLogCount());
    }


    @Test
    public void useLinkedListTest() throws ConfigurationException {
        appender.setEventsList(new LinkedList<>());
        long start = System.currentTimeMillis();
        for (int i = 0; i < 20000; i++) {
            Helpers.addSampleLoggingEvent(appender);
        }
        long end = System.currentTimeMillis();
        //90ms
        System.out.println("useLinkedListTest took " + (end - start) + " MilliSeconds");
    }

    @Test
    public void useArrayListTest() throws ConfigurationException {
        appender.setEventsList(new ArrayList<>());
        long start = System.currentTimeMillis();
        for (int i = 0; i < 20000; i++) {
            Helpers.addSampleLoggingEvent(appender);
        }
        long end = System.currentTimeMillis();
        //100ms
        System.out.println("useLinkedListTest took " + (end - start) + " MilliSeconds");
    }

    @Test
    public void consoleAppenderTest() throws ConfigurationException {
        ConsoleAppender consoleAppender = new ConsoleAppender();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 20000; i++) {
            Helpers.addSampleLoggingEvent(appender);
        }
        long end = System.currentTimeMillis();
        //337ms
        System.out.println("useLinkedListTest took " + (end - start) + " MilliSeconds");
    }

    @Test
    public void fileAppenderTest() throws ConfigurationException {
        FileAppender fileAppender = new FileAppender();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 20000; i++) {
            Helpers.addSampleLoggingEvent(appender);
        }
        long end = System.currentTimeMillis();
        //340ms
        System.out.println("useLinkedListTest took " + (end - start) + " MilliSeconds");
    }

    @Test
    public void patternLayoutTest() throws Exception {
        PatternLayout layout = new PatternLayout();
        layout.setConversionPattern("%m%n");
        appender.setLayout(layout);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 20000; i++) {
            Helpers.addSampleLoggingEvent(appender);
        }
        List<String> logs = appender.getEventStrings();
        long end = System.currentTimeMillis();
        //330ms
        System.out.println("useLinkedListTest took " + (end - start) + " MilliSeconds");
    }

    @Test
    public void velocityLayoutTest() throws Exception {
        VelocityLayout layout = new VelocityLayout();
        layout.setConversionPattern("%m%n");
        appender.setLayout(layout);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 20000; i++) {
            Helpers.addSampleLoggingEvent(appender);
        }
        List<String> logs = appender.getEventStrings();
        long end = System.currentTimeMillis();
        //660ms
        System.out.println("useLinkedListTest took " + (end - start) + " MilliSeconds");
    }
}
