package consulting.gazman.ipaas.workflow.messaging.model;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

// Generic Message model with flexible payload, ID placeholders, and timestamps
public class Message<T> {
    private UUID correlationId;
    private UUID messageId; // Placeholder for a unique message ID
    private LocalDateTime timestamp; // Timestamp for message creation
    private T payload; // Generic payload field to hold any type of data

    // Constructor
    public Message() {
        this.timestamp = LocalDateTime.now(); // Set the current timestamp when the message is created
    }

    // Optional getter for correlationId
    public Optional<UUID> getCorrelationId() {
        return Optional.ofNullable(correlationId);
    }

    public void setCorrelationId(UUID correlationId) {
        this.correlationId = correlationId;
    }

    // Optional getter for messageId
    public Optional<UUID> getMessageId() {
        return Optional.ofNullable(messageId);
    }

    public void setMessageId(UUID messageId) {
        this.messageId = messageId;
    }

    // Getter for timestamp
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    // Getter for payload (generic type)
    public Optional<T> getPayload() {
        return Optional.ofNullable(payload);
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }
}
