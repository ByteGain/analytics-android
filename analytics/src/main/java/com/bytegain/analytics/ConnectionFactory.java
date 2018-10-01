package com.bytegain.analytics;

import android.util.Base64;
import com.bytegain.analytics.core.BuildConfig;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Abstraction to customize how connections are created. This is can be used to point our SDK at
 * your proxy server for instance.
 */
public class ConnectionFactory {

  private static final boolean USE_LOCALHOST_SERVER = false;
  private static final int DEFAULT_READ_TIMEOUT_MILLIS = 20 * 1000; // 20s
  private static final int DEFAULT_CONNECT_TIMEOUT_MILLIS = 15 * 1000; // 15s
  static final String USER_AGENT = "analytics-android/" + BuildConfig.VERSION_NAME;

  private String authorizationHeader(String writeKey) {
    return "Basic " + Base64.encodeToString((writeKey + ":").getBytes(), Base64.NO_WRAP);
  }

  /** Return a {@link HttpURLConnection} that reads JSON formatted project settings. */
  public HttpURLConnection projectSettings(String writeKey) throws IOException {
    // ByteGain does not support this
    return null;
    //    return openConnection("https://cdn-settings.segment.com/v1/projects/" + writeKey + "/settings");
  }

  /**
   * Return a {@link HttpURLConnection} that writes batched payloads to {@code
   * https://api.segment.io/v1/import}.
   */
  public HttpURLConnection upload(String writeKey) throws IOException {
    // 10.0.2.2 is emulator's route to host's 127.0.0.1
    final String url =
        USE_LOCALHOST_SERVER ? "http://10.0.2.2:5001/v1/batch" : "https://js.bytegain.com/v1/batch";
    HttpURLConnection connection = openConnection(url);
    //  HttpURLConnection connection = openConnection("https://api.segment.io/v1/import");

    connection.setRequestProperty("Authorization", authorizationHeader(writeKey));
    connection.setRequestProperty("Content-Encoding", "gzip");
    connection.setDoOutput(true);
    //connection.setChunkedStreamingMode(0);
    return connection;
  }

  /**
   * Return a {@link HttpURLConnection} that writes gets attribution information from {@code
   * https://mobile-service.segment.com/attribution}.
   */
  public HttpURLConnection attribution(String writeKey) throws IOException {
    // ByteGain does not support this
    return null;
    //    HttpURLConnection connection =
    //        openConnection("https://mobile-service.segment.com/v1/attribution");
    //    connection.setRequestProperty("Authorization", authorizationHeader(writeKey));
    //    connection.setRequestMethod("POST");
    //    connection.setDoOutput(true);
    //    return connection;
  }

  /**
   * Configures defaults for connections opened with {@link #upload(String)}, {@link
   * #attribution(String)} and {@link #projectSettings(String)}.
   */
  protected HttpURLConnection openConnection(String url) throws IOException {
    HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
    connection.setConnectTimeout(DEFAULT_CONNECT_TIMEOUT_MILLIS);
    connection.setReadTimeout(DEFAULT_READ_TIMEOUT_MILLIS);
    connection.setRequestProperty("Content-Type", "application/json");
    connection.setRequestProperty("User-Agent", USER_AGENT);
    connection.setDoInput(true);
    return connection;
  }
}
