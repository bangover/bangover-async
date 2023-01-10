package cloud.bangover.async.promises;

import cloud.bangover.async.promises.Promise.FinalizingHandler;

public class MockFinalizingHandler implements FinalizingHandler {
  private boolean finalized = false;

  public boolean isFinalized() {
    return finalized;
  }

  @Override
  public void onComplete() {
    this.finalized = true;
  }
}
