package com.bytegain.analytics.test;

import static com.bytegain.analytics.Utils.createContext;
import static com.bytegain.analytics.Utils.createTraits;

import com.bytegain.analytics.AnalyticsContext;
import com.bytegain.analytics.Options;
import com.bytegain.analytics.Properties;
import com.bytegain.analytics.Traits;
import com.bytegain.analytics.integrations.TrackPayload;

@Deprecated
public class TrackPayloadBuilder {

  private AnalyticsContext context;
  private Traits traits;
  private String event;
  private Properties properties;
  private Options options;

  public TrackPayloadBuilder context(AnalyticsContext context) {
    this.context = context;
    return this;
  }

  public TrackPayloadBuilder traits(Traits traits) {
    this.traits = traits;
    return this;
  }

  public TrackPayloadBuilder event(String event) {
    this.event = event;
    return this;
  }

  public TrackPayloadBuilder properties(Properties properties) {
    this.properties = properties;
    return this;
  }

  public TrackPayloadBuilder options(Options options) {
    this.options = options;
    return this;
  }

  public TrackPayload build() {
    if (traits == null) {
      traits = createTraits();
    }
    if (event == null) {
      event = "bar";
    }
    if (context == null) {
      context = createContext(traits);
    }
    if (properties == null) {
      properties = new Properties();
    }
    if (options == null) {
      options = new Options();
    }
    return new TrackPayload.Builder()
        .event(event)
        .properties(properties)
        .anonymousId(traits.anonymousId())
        .context(context)
        .integrations(options.integrations())
        .build();
  }
}
