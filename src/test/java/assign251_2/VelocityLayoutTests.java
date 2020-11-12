package assign251_2;

import static org.junit.Assert.assertEquals;

import org.apache.log4j.Category;
import org.apache.log4j.Priority;
import org.apache.log4j.spi.LoggingEvent;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class VelocityLayoutTests {


    @Before
    public void basicTest() {
        LoggingEvent event = new LoggingEvent("testCategory", Category.getRoot(), Priority.DEBUG, "someMessage", new Exception("someException"));
        VelocityLayout layout = new VelocityLayout();
        assertEquals("someMessage", layout.format(event).trim());
    }

    @Test
    public void setConversionPatternTest() {
        LoggingEvent event = new LoggingEvent("testCategory", Category.getRoot(), Priority.DEBUG, "someMessage", new Exception("someException"));
        VelocityLayout layout = new VelocityLayout();
        layout.setConversionPattern("now is [$d] this is thread [$t] we got exception [$m]");
        String formattedLog = layout.format(event).trim();
        Assert.assertTrue(formattedLog.startsWith("now is ["));
        Assert.assertTrue(formattedLog.endsWith("this is thread [main] we got exception [someMessage]"));
    }

    @Test
    public void constructorTest() {
        LoggingEvent event = new LoggingEvent("testCategory", Category.getRoot(), Priority.DEBUG, "someMessage", new Exception("someException"));
        VelocityLayout layout = new VelocityLayout("we got exception [$m]");
        assertEquals("we got exception [someMessage]", layout.format(event).trim());
    }
}
