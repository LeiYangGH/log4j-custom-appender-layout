package assign251_2;

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
      throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
    Helpers.initEachAppender();
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
}
