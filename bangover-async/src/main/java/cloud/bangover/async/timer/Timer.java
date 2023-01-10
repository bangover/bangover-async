package cloud.bangover.async.timer;

import cloud.bangover.async.timer.TimeoutSupervisor.TimeoutCallback;
import lombok.SneakyThrows;

public final class Timer {
  /**
   * Sleep thread for period in milliseconds.
   *
   * @param milliseconds The amount of miliseconds
   */
  public static void sleep(Long milliseconds) {
    sleep(Timeout.ofMilliseconds(milliseconds));
  }

  /**
   * Sleep thread for specified timeout duration
   *
   * @param timeout The timeout object
   */
  @SneakyThrows
  public static void sleep(Timeout timeout) {
    Thread.sleep(timeout.getMilliseconds());
  }

  /**
   * Create timeout supervisor for specified timeout duration
   *
   * @param milliseconds The milliseconds delay
   * @param callback The timeout callback
   * @return The timeout supervisor
   */
  public static TimeoutSupervisor supervisor(Long milliseconds, TimeoutCallback callback) {
    return supervisor(Timeout.ofMilliseconds(milliseconds), callback);
  }
  
  /**
   * Create timeout supervisor for specified timeout duration
   * 
   * @param timeout  The timeout object
   * @param callback The timeout callback
   * @return The timeout supervisor
   */
  public static TimeoutSupervisor supervisor(Timeout timeout, TimeoutCallback callback) {
    return new TimeoutSupervisor(timeout, callback);
  }
}
