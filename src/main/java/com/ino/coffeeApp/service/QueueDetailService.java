package com.ino.coffeeApp.service;

import com.ino.coffeeApp.dto.QueueAvailableCountUpdateRequestDto;
import com.ino.coffeeApp.entity.QueueDetail;

import java.util.List;

public interface QueueDetailService {

    List<QueueDetail> getShopQueuesDetails(Long shopId);

    QueueDetail updateAvailableQueueSize(Long queueId, Boolean isOrdering, Long version);

    List<QueueDetail> getAvailableQueues(Long shopId);

}
