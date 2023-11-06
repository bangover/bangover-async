package cloud.bangover.async;

import java.util.Optional;

/**
 * This interface describes the context environment attributes, associated with the concrete
 * asynchronous invocation flow. It may be used if we want to pass some values, associated with the
 * concrete flow which could to be perform in the different threads and where we can't to be use
 * {@link ThreadLocal}. The {@link AsyncContext} lifecycle starts after call
 * {@link LifecycleController#createAsyncContext()} and completes after
 * {@link LifecycleController#destroyAsyncContext(String)}
 * 
 * @author Dmitry Mikhaylenko
 */
public interface AsyncContext {
  String getKey();

  /**
   * Get the async context attribute
   * 
   * @param key The attribute key
   * @return The attribute value, wrapped by {@link Optional}
   */
  Optional<Object> attribute(String key);

  /**
   * Set the async context attribute
   * 
   * @param key   The attribute key
   * @param value The attribute value
   */
  void attribute(String key, Object value);

  /**
   * This interface describes the asynchronous context lifecycle controller. It may be used to
   * perform operations over the asynchronous contexts.
   * 
   * @author Dmitry Mikhaylenko
   */
  interface LifecycleController {
    /**
     * Get the current context id for the current thread.
     * 
     * @return The context id, wrapped by {@link Optional}
     */
    Optional<String> getCurrentContextId();

    /**
     * Restore the passed context id as a current context id for the current thread.
     * 
     * @param contextId The context id, wrapped by {@link Optional}
     */
    void restoreCurrentContextId(Optional<String> contextId);

    /**
     * This method creates the {@link AsyncContext}.
     * 
     * @return The created {@link AsyncContext} identifier.
     */
    String createAsyncContext();

    /**
     * Get the current {@link AsyncContext}. If context is not exist, that the new context will be
     * created. Use the {@link LifecycleController#destroyAsyncContext(String)} to avoid memory leaks.
     * 
     * @return T The current async context
     */
    AsyncContext getCurrentContext();

    /**
     * Destroy the current {@link AsyncContext}
     */
    void destroyAsyncContext(String key);
  }
}
