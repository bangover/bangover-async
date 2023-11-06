package cloud.bangover.async;

import cloud.bangover.generators.Generator;
import cloud.bangover.generators.UuidGenerator;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import lombok.Getter;

public class Async {
  private static final Controller controller = new Controller();

  public static AsyncContext getCurrentContext() {
    return controller.getCurrentContext();
  }

  public static AsyncContext.LifecycleController getController() {
    return controller;
  }

  public static void executeInsideContext(Optional<String> contextId, Runnable runnable) {
    Optional<String> workerAsyncContextKey = controller.getCurrentContextId();
    try {
      controller.restoreCurrentContextId(contextId);
      runnable.run();
    } finally {
      controller.restoreCurrentContextId(workerAsyncContextKey);
    }
  }

  private static class Context implements AsyncContext {
    @Getter
    private final String key;
    private final Map<String, Object> attributes = new ConcurrentHashMap<String, Object>();

    private Context(String key) {
      super();
      this.key = key;
    }

    public Optional<Object> attribute(String key) {
      return Optional.ofNullable(this.attributes.get(key));
    }

    public void attribute(String key, Object value) {
      this.attributes.put(key, value);
    }
  }

  private static class Controller implements AsyncContext.LifecycleController {
    private final Generator<String> uuidGenerator = new UuidGenerator().generateStrings();
    private final ThreadLocal<String> currentKey = new ThreadLocal<String>();
    private final Map<String, Context> contexts = new ConcurrentHashMap<String, Context>();

    @Override
    public Optional<String> getCurrentContextId() {
      return Optional.ofNullable(currentKey.get());
    }

    @Override
    public void restoreCurrentContextId(Optional<String> contextId) {
      if (contextId.isPresent()) {
        this.currentKey.set(contextId.get());
      } else {
        this.currentKey.remove();
      }
    }

    @Override
    public String createAsyncContext() {
      Context context = new Context(uuidGenerator.generateNext());
      this.contexts.put(context.getKey(), context);
      this.currentKey.set(context.getKey());
      return context.getKey();
    }

    @Override
    public void destroyAsyncContext(String key) {
      this.contexts.remove(key);
    }

    @Override
    public AsyncContext getCurrentContext() {
      return getCurrentContextId().map(this.contexts::get).orElseGet(() -> {
        return new Context(uuidGenerator.generateNext());
      });
    }
  }
}
