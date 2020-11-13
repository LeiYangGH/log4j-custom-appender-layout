package assign251_2;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import javax.naming.ConfigurationException;
import org.apache.log4j.Category;
import org.apache.log4j.Priority;
import org.apache.log4j.spi.LoggingEvent;
import org.junit.Before;
import org.junit.Test;

public class MemAppenderTests {

  MemAppender appender = MemAppender.getInstance();

  //  https://stackoverflow.com/questions/8256989/singleton-and-unit-testing
  //  avoid concurrent tests execution affect each other
  @Before
  public void initEachAppenderTest()
      throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
    Field instance = MemAppender.class.getDeclaredField("uniqueInstance");
    instance.setAccessible(true);
    instance.set(null, null);
  }


  private void addSampleLoggingEvent() {
    LoggingEvent event = new LoggingEvent("", Category.getRoot(), Priority.DEBUG, "",
        new Exception(""));
    appender.append(event);
  }

  @Test
  public void DiscardedLogCountTest() throws ConfigurationException {
    appender.setMaxSize(2);
    appender.setLayout(new VelocityLayout());
    addSampleLoggingEvent();
    addSampleLoggingEvent();
    assertEquals(0, appender.getDiscardedLogCount());
    addSampleLoggingEvent();
    assertEquals(1, appender.getDiscardedLogCount());
    addSampleLoggingEvent();
    assertEquals(2, appender.getDiscardedLogCount());
  }

  @Test
  public void setArrayListTest() throws ConfigurationException {
    appender.setMaxSize(2);
    appender.setLayout(new VelocityLayout());
    addSampleLoggingEvent();
    addSampleLoggingEvent();
    appender.setEventsList(new ArrayList<>());
    assertEquals(0, appender.getDiscardedLogCount());
    addSampleLoggingEvent();
    assertEquals(0, appender.getDiscardedLogCount());
    addSampleLoggingEvent();
    assertEquals(0, appender.getDiscardedLogCount());
    addSampleLoggingEvent();
    assertEquals(1, appender.getDiscardedLogCount());
  }


  @Test(expected = ConfigurationException.class)
  public void layoutNotSetExceptionTest() throws Exception {
    appender.setMaxSize(2);
    addSampleLoggingEvent();
    appender.printLogs();
  }

  @Test(expected = InvalidParameterException.class)
  public void setMaxSize0Test() throws ConfigurationException {
    appender.setMaxSize(0);
  }

  @Test(expected = InvalidParameterException.class)
  public void setMaxSize1Test() throws ConfigurationException {
    appender.setMaxSize(1);
  }

  @Test(expected = ConfigurationException.class)
  public void setMaxSizeLessThanExistingTest() throws ConfigurationException {
    addSampleLoggingEvent();
    addSampleLoggingEvent();
    addSampleLoggingEvent();
    appender.setMaxSize(2);
  }
}
