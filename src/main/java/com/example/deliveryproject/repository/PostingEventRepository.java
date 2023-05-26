package com.example.deliveryproject.repository;

import com.example.deliveryproject.entity.Office;
import com.example.deliveryproject.entity.PostingEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostingEventRepository extends JpaRepository<PostingEvent, Long> {
    List<PostingEvent> findEventsByPostingNumber(String postingNumber);
}
