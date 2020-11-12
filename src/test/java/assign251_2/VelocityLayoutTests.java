package assign251_2;

import static org.junit.Assert.assertEquals;

import org.apache.log4j.Category;
import org.apache.log4j.Priority;
import org.apache.log4j.spi.LoggingEvent;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class VelocityLayoutTests {
    LoggingEvent event = new LoggingEvent("testCategory", Category.getRoot(), Priority.DEBUG, "someMessage", new Exception("someException"));
    VelocityLayout layout = new VelocityLayout();

    @Before
    public void init() {
        assertEquals("someMessage", layout.format(event).trim());
    }

    @Test
    public void toStringTest1() {
        layout.setConversionPattern("now is [$d] this is thread [$t] we got exception [$m]");
        String formattedLog = layout.format(event).trim();
        Assert.assertTrue(formattedLog.startsWith("now is ["));
        Assert.assertTrue(formattedLog.endsWith("this is thread [main] we got exception [someMessage]"));
    }
}
