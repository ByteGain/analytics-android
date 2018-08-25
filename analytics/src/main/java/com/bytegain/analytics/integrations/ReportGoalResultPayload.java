package com.bytegain.analytics.integrations;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bytegain.analytics.Properties;
import com.bytegain.analytics.internal.Private;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.bytegain.analytics.internal.Utils.assertNotNull;
import static com.bytegain.analytics.internal.Utils.assertNotNullOrEmpty;
import static com.bytegain.analytics.internal.Utils.isNullOrEmpty;

public class ReportGoalResultPayload extends BasePayload {

    static final String EVENT_KEY = "event";
    static final String PROPERTIES_KEY = "properties";
    private GoalResult result;

    public enum GoalResult {
        undefined,  // Not used
        success,    // Success following a prior attemptGoal
        failure,    // Failure following a prior attemptGoal
        unsolicitedSuccess  // Accomplishing goal without issuing a prior attemptGoal
    }

    @Private
    ReportGoalResultPayload(
            @NonNull String messageId,
            @NonNull Date timestamp,
            @NonNull Map<String, Object> context,
            @NonNull Map<String, Object> integrations,
            @Nullable String userId,
            @NonNull String anonymousId,
            @NonNull String event,
            @NonNull Map<String, Object> properties,
            @Nullable GoalResult result) {
        super(Type.track, messageId, timestamp, context, integrations, userId, anonymousId);

        this.result = result;

        put(EVENT_KEY, event);
        put(PROPERTIES_KEY, properties);
    }

    public GoalResult result() {
        return result;
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

    @Override
    public String toString() {
        return "ReportGoalResultPayload{event=\"" + event() + "\"}";
    }

    @NonNull
    @Override
    public ReportGoalResultPayload.Builder toBuilder() {
        return new ReportGoalResultPayload.Builder(this);
    }

    /** Fluent API for creating {@link ReportGoalResultPayload} instances. */
    public static class Builder extends BasePayload.Builder<ReportGoalResultPayload, ReportGoalResultPayload.Builder> {

        private String event;
        private Map<String, Object> properties;
        private GoalResult goalResult;

        public Builder() {
            // Empty constructor.
        }

        @Private
        Builder(ReportGoalResultPayload goal) {
            super(goal);
            event = goal.event();
            properties = goal.properties();
            goalResult = goal.result();
        }

        @NonNull
        public ReportGoalResultPayload.Builder result(@NonNull GoalResult result) {
            this.goalResult = result;
            return this;
        }

        @NonNull
        public ReportGoalResultPayload.Builder event(@NonNull String event) {
            this.event = assertNotNullOrEmpty(event, "intervention");
            return this;
        }

        @NonNull
        public ReportGoalResultPayload.Builder properties(@NonNull Map<String, ?> properties) {
            assertNotNull(properties, "properties");
            this.properties = Collections.unmodifiableMap(new LinkedHashMap<>(properties));
            return this;
        }

        @Override
        protected ReportGoalResultPayload realBuild(
                @NonNull String messageId,
                @NonNull Date timestamp,
                @NonNull Map<String, Object> context,
                @NonNull Map<String, Object> integrations,
                String userId,
                @NonNull String anonymousId) {
            assertNotNullOrEmpty(event, "intervention");

            Map<String, Object> properties = this.properties;
            if (isNullOrEmpty(properties)) {
                properties = Collections.emptyMap();
            }

            return new ReportGoalResultPayload(
                    messageId, timestamp, context, integrations, userId, anonymousId, event, properties, goalResult);
        }

        @Override
        ReportGoalResultPayload.Builder self() {
            return this;
        }
    }
}
