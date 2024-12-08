package com.ino.coffeeApp.controller;

import com.ino.coffeeApp.entity.QueueDetail;
import com.ino.coffeeApp.service.QueueDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/queue")
@RestController
public class QueueDetailController {

    private final QueueDetailService queueDetailService;

    @Autowired
    public QueueDetailController(QueueDetailService queueDetailService) {
        this.queueDetailService = queueDetailService;
    }

    @GetMapping("/shop-id/{shopId}")
    public ResponseEntity<Object> viewShopQueueDetail(@PathVariable Long shopId) {
        List<QueueDetail> shopQueuesDetails = queueDetailService.getShopQueuesDetails(shopId);

        return new ResponseEntity<>(shopQueuesDetails, HttpStatus.OK);
    }

    @GetMapping("/available-queues/{shopId}")
    public ResponseEntity<Object> getAvailableQueuesByShopId(@PathVariable Long shopId) {
        List<QueueDetail> shopQueuesDetails = queueDetailService.getAvailableQueues(shopId);

        return new ResponseEntity<>(shopQueuesDetails, HttpStatus.OK);
    }

    @PutMapping("/update-available-queue-size/{queueId}")
    public ResponseEntity<Object> updateAvailableQueueSize(@PathVariable Long queueId,
                                                           @RequestParam Boolean isOrdering,
                                                           @RequestParam Long version) {
        QueueDetail queueDetail = queueDetailService.updateAvailableQueueSize(queueId, isOrdering, version);
        return new ResponseEntity<>(queueDetail, HttpStatus.OK);
    }


}
