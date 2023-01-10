package cloud.bangover.async.timer;

import cloud.bangover.async.timer.TimeoutSupervisor.TimeoutCallback;

public class MockTimeoutCallback implements TimeoutCallback {
  private boolean timeoutHappened = false;
  
  public boolean isTimeoutHappened() {
    return this.timeoutHappened;
  }
  
  @Override
  public void onTimeout() {
    this.timeoutHappened = true;
  }
}
