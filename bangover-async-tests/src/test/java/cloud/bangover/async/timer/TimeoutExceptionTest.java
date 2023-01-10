package cloud.bangover.async.timer;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TimeoutExceptionTest {
  @Test
  public void shouldExceptionBeSuccessfullyCreatedForTimeout() {
    // Given
    Timeout timeout = Timeout.ofMilliseconds(1000L);
    // When
    TimeoutException exception = new TimeoutException(timeout);
    // Then
    Assert.assertEquals(
        "The actor response waiting time is over. Timeout is Timeout(amount=1000, unit=Millis).",
        exception.getMessage());
  }
}
