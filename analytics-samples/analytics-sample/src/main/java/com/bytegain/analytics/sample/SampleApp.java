package com.bytegain.analytics.sample;

import android.app.Application;
import android.util.Log;
import com.bytegain.analytics.Analytics;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class SampleApp extends Application {

  // https://segment.com/segment-engineering/sources/android-test/settings/keys
  //private static final String ANALYTICS_WRITE_KEY = "5m6gbdgho6";
  private static final String ANALYTICS_WRITE_KEY = "x7sPb4mmoHBesEwwJIa2XPOAGuSuALwk";

  @Override
  public void onCreate() {
    super.onCreate();
    Log.i("SampleApp", "In onCreate");
    CalligraphyConfig.initDefault(
        new CalligraphyConfig.Builder()
            .setDefaultFontPath("fonts/CircularStd-Book.otf")
            .setFontAttrId(R.attr.fontPath)
            .build());

    Log.i(
        "SampleApp",
        "After Calligraphy ##########################################################");

    // Initialize a new instance of the Analytics client.
    Analytics.Builder builder =
        new Analytics.Builder(this, ANALYTICS_WRITE_KEY)
            .trackApplicationLifecycleEvents()
            .trackAttributionInformation()
            .recordScreenViews();

    Log.i("SampleApp", "After Builder ##########################################################");

    // Set the initialized instance as a globally accessible instance.
    Analytics.setSingletonInstance(builder.build());

    // Now anytime you call Analytics.with, the custom instance will be returned.
    Analytics analytics = Analytics.with(this);

    // If you need to know when integrations have been initialized, use the onIntegrationReady
    // listener.
    analytics.onIntegrationReady(
        "Segment.io",
        new Analytics.Callback() {
          @Override
          public void onReady(Object instance) {
            Log.d("Segment Sample", "Segment integration ready.");
          }
        });

    Log.i("SampleApp", "End ##########################################################");
  }
}