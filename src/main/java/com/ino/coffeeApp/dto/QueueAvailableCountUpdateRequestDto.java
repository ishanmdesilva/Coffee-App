package com.ino.coffeeApp.dto;

import lombok.Getter;

public class QueueAvailableCountUpdateRequestDto {

    private Long queueId;
    private Boolean isOrdering;
    private Long version;

    public Long getQueueId() {
        return queueId;
    }

    public void setQueueId(Long queueId) {
        this.queueId = queueId;
    }

    public Boolean getOrdering() {
        return isOrdering;
    }

    public void setOrdering(Boolean ordering) {
        isOrdering = ordering;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
