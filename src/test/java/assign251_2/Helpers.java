package assign251_2;

import java.lang.reflect.Field;
import org.apache.log4j.Category;
import org.apache.log4j.Priority;
import org.apache.log4j.spi.LoggingEvent;

public class Helpers {

  public static void initEachAppender()
      throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
    Field instance = MemAppender.class.getDeclaredField("uniqueInstance");
    instance.setAccessible(true);
    instance.set(null, null);
  }

  public static LoggingEvent createSampleLoggingEvent() {
    return new LoggingEvent("", Category.getRoot(), Priority.DEBUG, "",
        new Exception(""));
  }

  public static void addSampleLoggingEvent(MemAppender appender) {
    appender.append(createSampleLoggingEvent());
  }
}
