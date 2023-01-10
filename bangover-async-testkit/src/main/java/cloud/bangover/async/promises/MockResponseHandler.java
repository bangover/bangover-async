package cloud.bangover.async.promises;

import cloud.bangover.MockHistory;
import cloud.bangover.async.promises.Promise.ResponseHandler;
import lombok.Getter;

@Getter
public final class MockResponseHandler<T> implements ResponseHandler<T> {
  private MockHistory<T> history = new MockHistory<T>();

  @Override
  public void onResponse(T response) {
    this.history.put(response);
  }
}
