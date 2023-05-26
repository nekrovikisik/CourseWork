package com.example.deliveryproject.service;

import com.example.deliveryproject.dto.PostingEventDto;

import java.util.List;

public interface PostingEventService {

    List<PostingEventDto> findEventsDtoByPostingNumber(String postingNumber);
}
