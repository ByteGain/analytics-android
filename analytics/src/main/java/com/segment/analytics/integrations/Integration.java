package com.segment.analytics.integrations;

import android.app.Activity;
import android.os.Bundle;
import com.segment.analytics.Analytics;
import com.segment.analytics.ValueMap;

/**
 * Converts Segment messages to a format a bundled integration understands, and calls those methods.
 *
 * @param <T> The type of the backing instance. This isn't strictly necessary (since we return an
 *     object), but serves as documentation for what type to expect with {@link
 *     #getUnderlyingInstance()}.
 */
public abstract class Integration<T> {

  public interface Factory {

    /**
     * Attempts to create an adapter for with {@code settings}. This returns the adapter if one was
     * created, or null if this factory isn't capable of creating such an adapter.
     */
    Integration<?> create(ValueMap settings, Analytics analytics);

    /** The key for which this factory can create an {@link Integration}. */
    String key();
  }

  /** @see android.app.Application.ActivityLifecycleCallbacks */
  public void onActivityCreated(Activity activity, Bundle savedInstanceState) {}

  /** @see android.app.Application.ActivityLifecycleCallbacks */
  public void onActivityStarted(Activity activity) {}

  /** @see android.app.Application.ActivityLifecycleCallbacks */
  public void onActivityResumed(Activity activity) {}

  /** @see android.app.Application.ActivityLifecycleCallbacks */
  public void onActivityPaused(Activity activity) {}

  /** @see android.app.Application.ActivityLifecycleCallbacks */
  public void onActivityStopped(Activity activity) {}

  /** @see android.app.Application.ActivityLifecycleCallbacks */
  public void onActivitySaveInstanceState(Activity activity, Bundle outState) {}

  /** @see android.app.Application.ActivityLifecycleCallbacks */
  public void onActivityDestroyed(Activity activity) {}

  /**
   * @see Analytics#identify(String, com.segment.analytics.Traits, com.segment.analytics.Options)
   */
  public void identify(IdentifyPayload identify) {}

  /** @see Analytics#group(String, com.segment.analytics.Traits, com.segment.analytics.Options) */
  public void group(GroupPayload group) {}

  /**
   * @see Analytics#track(String, com.segment.analytics.Properties, com.segment.analytics.Options)
   */
  public void track(TrackPayload track) {}

  public void attemptGoal(AttemptGoalPayload attemptGoal) {}

  /** @see Analytics#alias(String, com.segment.analytics.Options) */
  public void alias(AliasPayload alias) {}

  /**
   * @see Analytics#screen(String, String, com.segment.analytics.Properties,
   *     com.segment.analytics.Options)
   */
  public void screen(ScreenPayload screen) {}

  /** @see Analytics#flush() */
  public void flush() {}

  /** @see Analytics#reset() */
  public void reset() {}

  /**
   * The underlying instance for this provider - used for integration specific actions. This will
   * return {@code null} for SDK's that only provide interactions with static methods (e.g.
   * Localytics).
   */
  public T getUnderlyingInstance() {
    return null;
  }
}
