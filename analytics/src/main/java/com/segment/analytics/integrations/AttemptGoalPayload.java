/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Segment, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.segment.analytics.integrations;

import static com.segment.analytics.internal.Utils.assertNotNull;
import static com.segment.analytics.internal.Utils.assertNotNullOrEmpty;
import static com.segment.analytics.internal.Utils.isNullOrEmpty;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.segment.analytics.Properties;
import com.segment.analytics.internal.Private;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class AttemptGoalPayload extends BasePayload {

    static final String EVENT_KEY = "intervention";
    static final String PROPERTIES_KEY = "properties";

    private static int responseNumber = 0;
    private String responseID;

    public final YesCallback yesCallback;
    public final NoCallback noCallback;

    @Private
    AttemptGoalPayload(
            @NonNull String messageId,
            @NonNull Date timestamp,
            @NonNull Map<String, Object> context,
            @NonNull Map<String, Object> integrations,
            @Nullable String userId,
            @NonNull String anonymousId,
            @NonNull String event,
            @NonNull Map<String, Object> properties,
            @NonNull YesCallback yesCallback,          // TODO: Change to @NonNull
            @Nullable NoCallback noCallback) {
        super(Type.intervention, messageId, timestamp, context, integrations, userId, anonymousId);
        responseNumber += 1;
        responseID = "r" + responseNumber;
        put(EVENT_KEY, event);
        put(PROPERTIES_KEY, properties);
        put("responseId", responseID);

        this.yesCallback = yesCallback;
        this.noCallback = noCallback;
    }

    /**
     * The name of the event. We recommend using title case and past tense for event names, like
     * "Signed Up".
     */
    @NonNull
    public String event() {
        return getString(EVENT_KEY);
    }

    /**
     * A dictionary of properties that give more information about the event. We have a collection of
     * special properties that we recognize with semantic meaning. You can also add your own custom
     * properties.
     */
    @NonNull
    public Properties properties() {
        return getValueMap(PROPERTIES_KEY, Properties.class);
    }

    public String getResponseID() {
        return responseID;
    }

    @Override
    public String toString() {
        return "AttemptGoalPayload{event=\"" + event() + "\"}";
    }

    @NonNull
    @Override
    public AttemptGoalPayload.Builder toBuilder() {
        return new Builder(this);
    }


    public interface YesCallback {
        void callback(@Nullable String str);
    }

    public interface NoCallback {
        void callback();
    }

    /** Fluent API for creating {@link AttemptGoalPayload} instances. */
    public static class Builder extends BasePayload.Builder<AttemptGoalPayload, Builder> {

        private String event;
        private Map<String, Object> properties;

        private YesCallback yesCallback;
        private NoCallback noCallback;

        public Builder() {
            // Empty constructor.
        }

        @Private
        Builder(AttemptGoalPayload goal) {
            super(goal);
            event = goal.event();
            properties = goal.properties();
        }

        @NonNull
        public Builder event(@NonNull String event) {
            this.event = assertNotNullOrEmpty(event, "intervention");
            return this;
        }

        @NonNull
        public Builder properties(@NonNull Map<String, ?> properties) {
            assertNotNull(properties, "properties");
            this.properties = Collections.unmodifiableMap(new LinkedHashMap<>(properties));
            return this;
        }

        public Builder yesCallback(@NonNull YesCallback yesCallback) {
            this.yesCallback = yesCallback;
            return this;
        }

        public Builder noCallback(@Nullable NoCallback noCallback) {
            this.noCallback = noCallback;
            return this;
        }

        @Override
        protected AttemptGoalPayload realBuild(
                @NonNull String messageId,
                @NonNull Date timestamp,
                @NonNull Map<String, Object> context,
                @NonNull Map<String, Object> integrations,
                String userId,
                @NonNull String anonymousId) {
            assertNotNullOrEmpty(event, "Attempted Goal");

            Map<String, Object> properties = this.properties;
            if (isNullOrEmpty(properties)) {
                properties = Collections.emptyMap();
            }

            return new AttemptGoalPayload(
                    messageId, timestamp, context, integrations, userId, anonymousId, event, properties, yesCallback, noCallback);
        }

        @Override
        Builder self() {
            return this;
        }
    }
}
