package cloud.bangover.async.promises;

import cloud.bangover.MockHistory;
import cloud.bangover.async.promises.Promise.ErrorHandler;
import lombok.Getter;

@Getter
public final class MockErrorHandler<E extends Throwable> implements ErrorHandler<E> {
  private final MockHistory<E> history = new MockHistory<E>();
  
  @Override
  public void onError(E error) {
    this.history.put(error);
  }
}
