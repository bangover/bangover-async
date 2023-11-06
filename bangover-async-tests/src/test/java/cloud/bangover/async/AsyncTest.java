package cloud.bangover.async;

import cloud.bangover.async.AsyncContext.LifecycleController;
import java.util.Optional;
import java.util.concurrent.Executors;
import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class AsyncTest {
  private static final String ATTRIBUTE_KEY = "attribute";

  @Test
  @SneakyThrows
  public void shouldTransferContextThroughThreads() {
    // Given
    LifecycleController lifecycleController = Async.getController();

    // When
    // --> Create contex
    String createdContextId = lifecycleController.createAsyncContext();

    // --> Set context attribute
    Async.getCurrentContext().attribute(ATTRIBUTE_KEY, 1L);

    // --> Get current context id
    Optional<String> contextId = lifecycleController.getCurrentContextId();
    AsyncContext context = Executors.newSingleThreadExecutor().submit(() -> {
      // --> Restore context id
      Async.getController().restoreCurrentContextId(contextId);
      return Async.getCurrentContext();
    }).get();

    // --> Destroy context
    lifecycleController.destroyAsyncContext(createdContextId);

    // Then
    Assert.assertEquals(createdContextId, context.getKey());
    Assert.assertEquals(Optional.of(1L), context.attribute(ATTRIBUTE_KEY));
    Assert.assertNotEquals(createdContextId, Async.getCurrentContext().getKey());
  }
}
