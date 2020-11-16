package assign251_2;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import javax.naming.ConfigurationException;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.PatternLayout;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class StressTests {

  MemAppender appender = MemAppender.getInstance();
  static MemAppender staticAppender = MemAppender.getInstance();
  private AppenderSkeleton appenderSkeleton;
  private Layout layout;
  private long eventsCount;

  public StressTests(AppenderSkeleton appenderSkeleton, Layout layout, long eventsCount) {
    this.appenderSkeleton = appenderSkeleton;
    this.layout = layout;
    this.eventsCount = eventsCount;
  }


  @Parameters
  @SuppressWarnings("unchecked")
  public static Collection prepareData() {
    Object[][] object = {
        {staticAppender, new VelocityLayout(), 1000},//1000 is relatively small
        {staticAppender, new VelocityLayout(), 20000},//2000 is more than default maxSize
        {staticAppender, new PatternLayout(), 1000},
        {staticAppender, new PatternLayout(), 20000},

        {new ConsoleAppender(), new VelocityLayout(), 1000},
        {new ConsoleAppender(), new VelocityLayout(), 20000},
        {new ConsoleAppender(), new PatternLayout(), 1000},
        {new ConsoleAppender(), new PatternLayout(), 20000},

        {new FileAppender(), new VelocityLayout(), 1000},
        {new FileAppender(), new VelocityLayout(), 20000},
        {new FileAppender(), new PatternLayout(), 1000},
        {new FileAppender(), new PatternLayout(), 20000},
    };

    return Arrays.asList(object);
  }

  @Before
  public void initEachAppenderTest()
      throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
    Helpers.initEachAppender();
  }

  @Test
  public void velocityLayoutTest() throws Exception {
    appenderSkeleton.setLayout(layout);
    long start = System.currentTimeMillis();
    for (int i = 0; i < eventsCount; i++) {
      Helpers.addSampleLoggingEvent(appender);
    }
    long end = System.currentTimeMillis();
    //manually collect results(time and memory) and do analysis
    System.out.println(String.format("%s, %s, %d  took %d MilliSeconds",
        appenderSkeleton.getClass(),
        layout.getClass(),
        eventsCount,
        (end - start)));
  }
}
