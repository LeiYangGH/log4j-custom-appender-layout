import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Main {

  protected static final Logger logger = LogManager.getLogger(Main.class);

  public static void main(String[] args) {
    logger.info("this is some log");
    System.out.println("Hello maven");
  }
}
