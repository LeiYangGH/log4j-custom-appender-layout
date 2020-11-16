package assign251_2;

import static org.junit.Assert.assertEquals;

import javax.naming.ConfigurationException;
import org.apache.log4j.Category;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.Priority;
import org.apache.log4j.spi.LoggingEvent;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CombinationsTests {
  //The test cases pass if no exception thrown

  MemAppender appender = MemAppender.getInstance();

  @Before
  public void initEachAppenderTest()
      throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException, ConfigurationException {
    Helpers.initEachAppender();
    appender.setMaxSize(10);
  }

  @Test
  public void MemAppender_VelocityLayout_Test() throws Exception {
    VelocityLayout layout = new VelocityLayout();
    appender.setLayout(layout);
    layout.setConversionPattern("now is [$d] this is thread [$t] we got exception [$m]");
    Helpers.addSampleLoggingEvent(appender);
    appender.printLogs();
  }

  @Test
  public void MemAppender_PatternLayout_Test() throws Exception {
    PatternLayout layout = new PatternLayout();
    appender.setLayout(layout);
    layout.setConversionPattern("now is [%d] this is thread [%t] we got exception [%m]");
    Helpers.addSampleLoggingEvent(appender);
    appender.printLogs();
  }

  @Test
  public void ConsoleAppender_VelocityLayout_Test() throws Exception {
    VelocityLayout layout = new VelocityLayout();
    ConsoleAppender theAppender = new ConsoleAppender(layout);
    layout.setConversionPattern("now is [%d] this is thread [%t] we got exception [%m]");
    theAppender.append(Helpers.createSampleLoggingEvent());
  }

  //add maxSize * 2 logs
  @Test
  public void add2MaxLogsTest() throws ConfigurationException {
    appender.setLayout(new VelocityLayout());
    for (int i = 0; i < appender.getMaxSize() * 2; i++) {
      Helpers.addSampleLoggingEvent(appender);
    }
    assertEquals(appender.getMaxSize(), appender.getCurrentLogs().size());
    assertEquals(appender.getMaxSize() * 2 - appender.getMaxSize(),
        appender.getDiscardedLogCount());
  }

  @Test
  public void setMaxSizePlus1ThenAddMaxLogsTest() throws ConfigurationException {
    long newMax = appender.getMaxSize() + 1;
    appender.setMaxSize(newMax);
    appender.setLayout(new VelocityLayout());
    for (int i = 0; i < newMax; i++) {
      Helpers.addSampleLoggingEvent(appender);
    }
    assertEquals(newMax, appender.getCurrentLogs().size());
    assertEquals(0, appender.getDiscardedLogCount());
  }
}
