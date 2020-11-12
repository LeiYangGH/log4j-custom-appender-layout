package assign251_2;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class VelocityLayoutTests {

  @Test
  public void toStringTest() {
    VelocityLayout layout = new VelocityLayout();
    assertEquals("Hello leiyang, nice to meet you!", layout.format(null));
  }
}
