package cloud.bangover.async.timer;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TimeoutTest {
  private static final Long AMOUNT = 10L;
  
  @Test
  public void shouldCreateOfMilliseconds() {
    checkCreatedTimeout(Timeout.ofMilliseconds(AMOUNT), AMOUNT);
  }
  
  @Test
  public void shouldCreateOfSeconds() {
    checkCreatedTimeout(Timeout.ofSeconds(AMOUNT), AMOUNT * 1000);  
  }
  
  @Test
  public void shouldCreateOfMinutes() {
    checkCreatedTimeout(Timeout.ofMinutes(AMOUNT), AMOUNT * 1000 * 60);  
  }
  
  @Test
  public void shouldCreateOfHours() {
    checkCreatedTimeout(Timeout.ofHours(AMOUNT), AMOUNT * 1000 * 60 * 60);  
  }
  
  @Test
  public void shouldCreateOfDays() {
    checkCreatedTimeout(Timeout.ofDays(AMOUNT), AMOUNT * 1000 * 60 * 60 * 24);  
  }
  
  private void checkCreatedTimeout(Timeout timeout, Long expectedTimeout) {
    Assert.assertEquals(expectedTimeout, timeout.getMilliseconds());
  }
}
