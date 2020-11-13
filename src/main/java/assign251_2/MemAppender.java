package assign251_2;

import javax.naming.ConfigurationException;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class MemAppender extends AppenderSkeleton {


  private long maxSize = 10000;//default
  private long discardedLogCount = 0;

  private static MemAppender uniqueInstance;
  private List<LoggingEvent> lstEvents = new CopyOnWriteArrayList<>();

  private MemAppender() {
  }

  public static MemAppender getInstance() {
    if (uniqueInstance == null) {
      synchronized (MemAppender.class) {
        // check again to avoid multi-thread access
        if (uniqueInstance == null) {
          uniqueInstance = new MemAppender();
        }
      }
    }
    return uniqueInstance;
  }

  public long getMaxSize() {
    return this.maxSize;
  }

  public void setEventsList(List<LoggingEvent> lst) {
    if (lst == null) {
      throw new InvalidParameterException("Cannot pass a null list to MemAppender!");
    } else if (lst.isEmpty()) {
      if (!this.lstEvents.isEmpty()) {
        System.out.println(
            "Warn: the existing logs will be cleared to accept the new LoggingEvent list!");
      }
      this.discardedLogCount = 0;
      this.lstEvents = lst;
    } else {
      throw new RuntimeException(
          "You must set an emtpy list to MemAppender before it collects logs!");
    }
  }

  public void setMaxSize(long value) throws ConfigurationException {
    if (value <= 1) {
      throw new InvalidParameterException("maxSize should be bigger than 0!");
    } else if (value < lstEvents.size()) {
      throw new ConfigurationException("maxSize should be bigger than current list size!");
    } else {
      this.maxSize = value;
    }
  }

  public long getDiscardedLogCount() {
    return this.discardedLogCount;
  }

  @Override
  protected void append(LoggingEvent loggingEvent) {
    if (this.lstEvents.size() >= this.maxSize) {
      this.lstEvents.remove(0);
      this.discardedLogCount++;
    }
    this.lstEvents.add(loggingEvent);
  }

  @Override
  public void close() {

  }

  @Override
  public boolean requiresLayout() {
    return true;
  }

  public List<LoggingEvent> getCurrentLogs() {
    return Collections.unmodifiableList(this.lstEvents);
  }

  public List<String> getEventStrings() throws Exception {
    if (this.layout == null) {
      throw new ConfigurationException("The layout must be set before getting formatted logs!");
    }
    return Collections.unmodifiableList(this.getCurrentLogs().stream().map(x ->
        this.layout.format(x)).collect(Collectors.toList()));
  }

  public void printLogs() throws Exception {
    for (String s : getEventStrings()) {
      System.out.println(s);
    }
    this.lstEvents.clear();
  }

}
