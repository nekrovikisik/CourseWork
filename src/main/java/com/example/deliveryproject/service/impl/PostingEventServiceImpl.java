package com.example.deliveryproject.service.impl;

import com.example.deliveryproject.dto.PostingEventDto;
import com.example.deliveryproject.entity.PostingEvent;
import com.example.deliveryproject.repository.*;
import com.example.deliveryproject.service.PostingEventService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostingEventServiceImpl implements PostingEventService {
    private PostingRepository postingRepository;
    private UserRepository userRepository;
    private DeliveryTariffRepository deliveryTariffRepository;

    private OfficeRepository officeRepository;
    private PostingEventRepository postingEventRepository;

    public PostingEventServiceImpl (PostingRepository postingRepository,
                                    UserRepository userRepository,
                                    DeliveryTariffRepository deliveryTariffRepository,
                                    OfficeRepository officeRepository,
                                    PostingEventRepository postingEventRepository) {
        this.postingRepository = postingRepository;
        this.userRepository = userRepository;
        this.deliveryTariffRepository = deliveryTariffRepository;
        this.officeRepository = officeRepository;
        this.postingEventRepository = postingEventRepository;
    }

    @Override
    public List<PostingEventDto> findEventsDtoByPostingNumber(String postingNumber) {
        return postingEventRepository.findEventsByPostingNumber(postingNumber).stream().map((posting) -> convertEntityToDto(posting))
                .collect(Collectors.toList());
    }
    private PostingEventDto convertEntityToDto(PostingEvent postingEvent) {
        PostingEventDto postingEventDto = new PostingEventDto();
        postingEventDto.setPostingNumber(postingEvent.getPosting().getPostingNumber());
        postingEventDto.setStatus(postingEvent.getPostingStatus().getStatusName());
        postingEventDto.setCreatedAt(postingEvent.getCreatedAt());

        return postingEventDto;
    }
    // convertToDto
    // SaveEvent
    // CreateEvent
    // где поставить бизнес-длогику для сохранения ?????????????????????????????????????
    //
}
