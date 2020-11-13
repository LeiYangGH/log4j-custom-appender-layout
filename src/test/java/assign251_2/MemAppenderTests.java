package assign251_2;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import javax.naming.ConfigurationException;
import org.apache.log4j.Category;
import org.apache.log4j.Priority;
import org.apache.log4j.spi.LoggingEvent;
import org.junit.Test;

public class MemAppenderTests {

  MemAppender appender = MemAppender.getInstance();

  private void addSampleLoggingEvent() {
    LoggingEvent event = new LoggingEvent("", Category.getRoot(), Priority.DEBUG, "",
        new Exception(""));
    appender.append(event);
  }

  @Test
  public void DiscardedLogCountTest() {
    appender.setMaxSize(2);
    appender.setLayout(new VelocityLayout());
    appender.setEventsList(new ArrayList<>());
    addSampleLoggingEvent();
    addSampleLoggingEvent();
    assertEquals(0, appender.getDiscardedLogCount());
    addSampleLoggingEvent();
    assertEquals(1, appender.getDiscardedLogCount());
    addSampleLoggingEvent();
    assertEquals(2, appender.getDiscardedLogCount());
  }

  @Test
  public void setArrayListTest() {
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
    appender.setEventsList(new ArrayList<>());
    appender.setMaxSize(2);
    addSampleLoggingEvent();
    appender.printLogs();
  }

}
