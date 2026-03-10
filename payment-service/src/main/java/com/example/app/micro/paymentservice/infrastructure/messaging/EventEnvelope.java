package com.example.app.micro.paymentservice.infrastructure.messaging;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EventEnvelope {
    private String eventId;
    private String eventType;
    private String eventVersion;
    private String timestamp;
    private String correlationId;
    private Map<String, Object> payload;

    public static EventEnvelope of(String eventType, String correlationId, Map<String, Object> payload) {
        return EventEnvelope.builder()
                .eventId(UUID.randomUUID().toString())
                .eventType(eventType)
                .eventVersion("v1")
                .timestamp(Instant.now().toString())
                .correlationId(correlationId)
                .payload(payload)
                .build();
    }
}
