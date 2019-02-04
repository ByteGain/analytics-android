analytics-android
=================

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.bytegain.analytics/analytics/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.bytegain.analytics/analytics)
[![Javadocs](http://javadoc-badge.appspot.com/com.bytegain.analytics/analytics.svg?label=javadoc)](http://javadoc-badge.appspot.com/com.bytegain.analytics/analytics)

analytics-android is an Android client for [ByteGain](https://bytegain.com)

## Documentation

See analytics-sample for an example Android App.

In your app's class that extends `android.app.Application`, initialize a `com.bytegain.analytics.Analytics` object:
```
import com.bytegain.analytics.Analytics;
...
  private static final String ANALYTICS_API_KEY = "YOUR_API_KEY_GOES_HERE";

  @Override
  public void onCreate() {
    super.onCreate();

    // Initialize a new instance of the Analytics client.
    Analytics.Builder builder =
        new Analytics.Builder(this, ANALYTICS_API_KEY)
            .trackApplicationLifecycleEvents()
            .trackAttributionInformation()
            .recordScreenViews();

    // Set the initialized instance as a globally accessible instance.
    Analytics.setSingletonInstance(builder.build());

    // Now anytime you call Analytics.with, the custom instance will be returned.
    Analytics analytics = Analytics.with(this);
```
The above sets up automatic reporting of screen views and application life cycle events to ByteGain servers.

To send additional event data to ByteGain servers, e.g., button clicks, modify your `android.app.Activity` subclass to add calls to the `track` method:
```
  @OnClick(R.id.action_track_a)
  void onButtonAClicked() {
    Analytics.with(this).track("Button A Clicked");
  }

  @OnClick(R.id.action_track_b)
  void onButtonBClicked() {
    Analytics.with(this).track("Button B Clicked");
  }
```

For more examples, see [analytics-samples/analytics-sample/src/main/java/com/bytegain/analytics/sample/MainActivity.java](./analytics-samples/analytics-sample/src/main/java/com/bytegain/analytics/sample/MainActivity.java).

The sample app has been run and tested using AndroidStudio version 3.3 and gradlew version 4.6.

## License

```
WWWWWW||WWWWWW
 W W W||W W W
      ||
    ( OO )__________
     /  |           \
    /o o|    MIT     \
    \___/||_||__||_|| *
         || ||  || ||
        _||_|| _||_||
       (__|__|(__|__|

The MIT License (MIT)

Copyright (c) 2018 ByteGain, Inc.
Copyright (c) 2016 Segment, Inc.

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
