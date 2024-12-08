package com.ino.coffeeApp.service.impl;

import com.ino.coffeeApp.dto.QueueAvailableCountUpdateRequestDto;
import com.ino.coffeeApp.entity.QueueDetail;
import com.ino.coffeeApp.exception.ValidateRecordException;
import com.ino.coffeeApp.repository.QueueDetailRepository;
import com.ino.coffeeApp.service.QueueDetailService;
import com.ino.coffeeApp.util.ErrorCodes;
import org.hibernate.sql.ast.tree.expression.Collation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QueueDetailServiceImpl implements QueueDetailService {

    private final QueueDetailRepository queueDetailRepository;

    @Autowired
    public QueueDetailServiceImpl(QueueDetailRepository queueDetailRepository) {
        this.queueDetailRepository = queueDetailRepository;
    }

    @Override
    public List<QueueDetail> getShopQueuesDetails(Long shopId) {
        List<QueueDetail> byShopDetailId = queueDetailRepository.findByShopDetailId(shopId);

        if(byShopDetailId.isEmpty())
            throw new ValidateRecordException("No Record found", ErrorCodes.NO_RECORD_FOUND);

        return byShopDetailId;
    }

    @Override
    public QueueDetail updateAvailableQueueSize(Long queueId, Boolean isOrdering, Long version) {
        Optional<QueueDetail> optQueueDetail = queueDetailRepository.findById(queueId);
        if(optQueueDetail.isEmpty()) {
            throw new ValidateRecordException("Queue is not found", ErrorCodes.ENTITY_NOT_FOUND);
        }
        QueueDetail queueDetail = optQueueDetail.get();

        // Version validate
        if(!version.equals(queueDetail.getVersion())) {
            throw new ValidateRecordException("Some one updated the record.", ErrorCodes.UNPROCESSABLE_ENTITY);
        }

        Integer availableCount = queueDetail.getAvailableCount();

        if(isOrdering != null && isOrdering) {
            // Check queue availability
            if(availableCount.equals(queueDetail.getSize())) {
                throw new ValidateRecordException("Queue is full.",ErrorCodes.UNPROCESSABLE_ENTITY);
            }
            queueDetail.setAvailableCount(availableCount+1);

        } else {
            queueDetail.setAvailableCount(availableCount-1);
        }

        return queueDetailRepository.save(queueDetail);
    }

    @Override
    public List<QueueDetail> getAvailableQueues(Long shopId) {
        List<QueueDetail> queueList = getShopQueuesDetails(shopId);

        queueList = queueList.stream()
                .filter(queue -> (queue.getAvailableCount() < queue.getSize()))
                .toList();
        if (queueList.isEmpty()) {
            throw new ValidateRecordException("No available queues.",ErrorCodes.ENTITY_NOT_FOUND);
        }
        return queueList;
    }
}
