package cloud.bangover.async.timer;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TimeoutSupervisorTest {
  @Test
  public void shouldBeCompletedWithoutTimeout() {
    // Given
    Timeout timeout = Timeout.ofMilliseconds(100L);
    MockTimeoutCallback timeoutCallback = new MockTimeoutCallback();
    TimeoutSupervisor supervisor = Timer.supervisor(timeout, timeoutCallback);
    // When
    supervisor.startSupervision();
    Timer.sleep(30L);
    supervisor.stopSupervision();
    Timer.sleep(100L);
    // Then
    Assert.assertFalse(timeoutCallback.isTimeoutHappened());
  }

  @Test
  public void shouldBeCompletedWithTimeoutOnCompleteWhenTimeoutIsExceeded() {
    // Given
    Timeout timeout = Timeout.ofMilliseconds(30L);
    MockTimeoutCallback timeoutCallback = new MockTimeoutCallback();
    TimeoutSupervisor supervisor = Timer.supervisor(timeout, timeoutCallback);
    // When
    supervisor.startSupervision();
    Timer.sleep(100L);
    supervisor.stopSupervision();
    // Then
    Assert.assertTrue(timeoutCallback.isTimeoutHappened());
  }

  @Test
  public void shouldBeCompletedWithTimeoutWhenItWasntCompleted() {
    // Given
    MockTimeoutCallback timeoutCallback = new MockTimeoutCallback();
    TimeoutSupervisor supervisor = Timer.supervisor(30L, timeoutCallback);
    // When
    supervisor.startSupervision();
    Timer.sleep(100L);
    // Then
    Assert.assertTrue(timeoutCallback.isTimeoutHappened());
  }
}
