package assign251_2;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;

public class MemAppender extends AppenderSkeleton {

  @Override
  protected void append(LoggingEvent loggingEvent) {

  }

  @Override
  public void close() {

  }

  @Override
  public boolean requiresLayout() {
    return false;
  }
}
