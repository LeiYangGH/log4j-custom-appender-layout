package assign251_2;

import java.io.StringWriter;
import java.util.Date;

import org.apache.log4j.Layout;
import org.apache.log4j.helpers.PatternConverter;
import org.apache.log4j.helpers.PatternParser;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

public class VelocityLayout extends Layout {

    public static final String DEFAULT_CONVERSION_PATTERN = "$m$n";
    private String pattern;

    public VelocityLayout() {
        this("$m$n");
    }

    public VelocityLayout(String pattern) {
        if (pattern != null) {
            this.pattern = pattern;
        }
    }


    public boolean ignoresThrowable() {
        return true;
    }

    public String format(LoggingEvent event) {
        VelocityContext context = new VelocityContext();

        StringWriter sw = new StringWriter();
        String vt = this.pattern;
        context.put("c", event.getLoggerName());
        context.put("d", new Date().toString());
        context.put("m", event.getMessage());
        context.put("p", event.getProperty("root"));
        context.put("t", event.getThreadName());
        context.put("n", System.lineSeparator());
        Velocity.evaluate(context, sw, "", vt);
        return sw.toString();
    }

    public void setConversionPattern(String conversionPattern) {
        this.pattern = conversionPattern;
    }

    public String getConversionPattern() {
        return this.pattern;
    }


    @Override
    public void activateOptions() {

    }
}
