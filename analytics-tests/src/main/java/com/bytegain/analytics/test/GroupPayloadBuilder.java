package com.bytegain.analytics.test;

import static com.bytegain.analytics.Utils.createContext;
import static com.bytegain.analytics.Utils.createTraits;

import com.bytegain.analytics.AnalyticsContext;
import com.bytegain.analytics.Options;
import com.bytegain.analytics.Traits;
import com.bytegain.analytics.integrations.GroupPayload;

@Deprecated
public class GroupPayloadBuilder {

  private AnalyticsContext context;
  private String groupId;
  private Traits traits;
  private Traits groupTraits;
  private Options options;

  public GroupPayloadBuilder context(AnalyticsContext context) {
    this.context = context;
    return this;
  }

  public GroupPayloadBuilder groupId(String groupId) {
    this.groupId = groupId;
    return this;
  }

  public GroupPayloadBuilder traits(Traits traits) {
    this.traits = traits;
    return this;
  }

  public GroupPayloadBuilder groupTraits(Traits groupTraits) {
    this.groupTraits = groupTraits;
    return this;
  }

  public GroupPayloadBuilder options(Options options) {
    this.options = options;
    return this;
  }

  public GroupPayload build() {
    if (traits == null) {
      traits = createTraits();
    }
    if (groupTraits == null) {
      groupTraits = new Traits();
    }
    if (context == null) {
      context = createContext(traits);
    }
    if (options == null) {
      options = new Options();
    }
    if (groupId == null) {
      groupId = "bar";
    }

    return new GroupPayload.Builder()
        .groupId(groupId)
        .traits(groupTraits)
        .anonymousId(traits.anonymousId())
        .context(context)
        .integrations(options.integrations())
        .build();
  }
}
