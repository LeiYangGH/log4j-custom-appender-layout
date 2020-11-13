package assign251_2;

import static org.junit.Assert.assertEquals;

import javax.naming.ConfigurationException;
import org.junit.Before;
import org.junit.Test;

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

}
