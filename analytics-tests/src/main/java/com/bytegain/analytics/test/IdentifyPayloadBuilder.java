package com.bytegain.analytics.test;

import static com.bytegain.analytics.Utils.createContext;
import static com.bytegain.analytics.Utils.createTraits;

import com.bytegain.analytics.AnalyticsContext;
import com.bytegain.analytics.Options;
import com.bytegain.analytics.Traits;
import com.bytegain.analytics.integrations.IdentifyPayload;

@Deprecated
public class IdentifyPayloadBuilder {

  private AnalyticsContext context;
  private Traits traits;
  private Options options;

  public IdentifyPayloadBuilder traits(Traits traits) {
    this.traits = traits;
    return this;
  }

  public IdentifyPayloadBuilder options(Options options) {
    this.options = options;
    return this;
  }

  public IdentifyPayloadBuilder context(AnalyticsContext context) {
    this.context = context;
    return this;
  }

  public IdentifyPayload build() {
    if (traits == null) {
      traits = createTraits();
    }
    if (context == null) {
      context = createContext(traits);
    }
    if (options == null) {
      options = new Options();
    }

    return new IdentifyPayload.Builder()
        .userId(traits.userId())
        .traits(traits)
        .anonymousId(traits.anonymousId())
        .context(context)
        .integrations(options.integrations())
        .build();
  }
}
