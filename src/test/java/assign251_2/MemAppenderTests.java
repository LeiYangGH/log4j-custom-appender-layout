package assign251_2;

import static org.junit.Assert.assertEquals;

import org.apache.log4j.Category;
import org.apache.log4j.Priority;
import org.apache.log4j.spi.LoggingEvent;
import org.junit.Test;

public class MemAppenderTests {

  @Test
  public void DiscardedLogCountTest() {
    MemAppender appender = new MemAppender();
    appender.setMaxSize(2);
    appender.setLayout(new VelocityLayout());
    appender
        .append(new LoggingEvent("", Category.getRoot(), Priority.DEBUG, "", new Exception("")));
    assertEquals(0, appender.getDiscardedLogCount());

    appender
        .append(new LoggingEvent("", Category.getRoot(), Priority.DEBUG, "", new Exception("")));
    assertEquals(0, appender.getDiscardedLogCount());

    appender
        .append(new LoggingEvent("", Category.getRoot(), Priority.DEBUG, "", new Exception("")));
    assertEquals(1, appender.getDiscardedLogCount());

    appender
        .append(new LoggingEvent("", Category.getRoot(), Priority.DEBUG, "", new Exception("")));
    assertEquals(2, appender.getDiscardedLogCount());
  }
}
