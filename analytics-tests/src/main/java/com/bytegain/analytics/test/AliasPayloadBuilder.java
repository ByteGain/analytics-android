package com.bytegain.analytics.test;

import static com.bytegain.analytics.Utils.createContext;
import static com.bytegain.analytics.Utils.createTraits;

import com.bytegain.analytics.AnalyticsContext;
import com.bytegain.analytics.Options;
import com.bytegain.analytics.Traits;
import com.bytegain.analytics.integrations.AliasPayload;

@Deprecated
public class AliasPayloadBuilder {

  private AnalyticsContext context;
  private Traits traits;
  private String newId;
  private Options options;

  public AliasPayloadBuilder traits(Traits traits) {
    this.traits = traits;
    return this;
  }

  public AliasPayloadBuilder context(AnalyticsContext context) {
    this.context = context;
    return this;
  }

  public AliasPayloadBuilder newId(String newId) {
    this.newId = newId;
    return this;
  }

  public AliasPayloadBuilder options(Options options) {
    this.options = options;
    return this;
  }

  public AliasPayload build() {
    if (traits == null) {
      traits = createTraits();
    }
    if (context == null) {
      context = createContext(traits);
    }
    if (options == null) {
      options = new Options();
    }
    if (newId == null) {
      newId = "foo";
    }
    return new AliasPayload.Builder()
        .userId(newId)
        .previousId(traits.currentId())
        .anonymousId(traits.anonymousId())
        .context(context)
        .integrations(options.integrations())
        .build();
  }
}
