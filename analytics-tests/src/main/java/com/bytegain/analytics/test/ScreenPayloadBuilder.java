package com.bytegain.analytics.test;

import static com.bytegain.analytics.Utils.createContext;
import static com.bytegain.analytics.Utils.createTraits;

import com.bytegain.analytics.AnalyticsContext;
import com.bytegain.analytics.Options;
import com.bytegain.analytics.Properties;
import com.bytegain.analytics.Traits;
import com.bytegain.analytics.integrations.ScreenPayload;

@Deprecated
public class ScreenPayloadBuilder {

  private AnalyticsContext context;
  private Traits traits;
  private String category;
  private String name;
  private Properties properties;
  private Options options;

  public ScreenPayloadBuilder context(AnalyticsContext context) {
    this.context = context;
    return this;
  }

  public ScreenPayloadBuilder traits(Traits traits) {
    this.traits = traits;
    return this;
  }

  public ScreenPayloadBuilder category(String category) {
    this.category = category;
    return this;
  }

  public ScreenPayloadBuilder name(String name) {
    this.name = name;
    return this;
  }

  public ScreenPayloadBuilder properties(Properties properties) {
    this.properties = properties;
    return this;
  }

  public ScreenPayloadBuilder options(Options options) {
    this.options = options;
    return this;
  }

  public ScreenPayload build() {
    if (traits == null) {
      traits = createTraits();
    }
    if (context == null) {
      context = createContext(traits);
    }
    if (options == null) {
      options = new Options();
    }
    if (category == null && name == null) {
      category = "foo";
      name = "bar";
    }
    if (properties == null) {
      properties = new Properties();
    }

    return new ScreenPayload.Builder()
        .category(category)
        .name(name)
        .properties(properties)
        .anonymousId(traits.anonymousId())
        .context(context)
        .integrations(options.integrations())
        .build();
  }
}
