package com.example.deliveryproject.service;

import com.example.deliveryproject.dto.PostingDto;
import com.example.deliveryproject.entity.Posting;

import java.util.List;

public interface PostingService {
    void savePosting(PostingDto postingDto);
    Posting findByPostingNumber(String postingNumber);

    List<PostingDto> findAllPostings();
    Posting findById(Long id);
    Posting getPostingByPostingNumber(String postingNumber);

    Posting updatePosting(Posting posting);

    void deletePostingByPostingNumber(String postingNumber);


}
