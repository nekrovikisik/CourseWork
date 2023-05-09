package com.example.deliveryproject.service.impl;

import com.example.deliveryproject.dto.PostingDto;
import com.example.deliveryproject.entity.Posting;
import com.example.deliveryproject.entity.User;
import com.example.deliveryproject.entity.DeliveryTariff;

import com.example.deliveryproject.repository.PostingRepository;
import com.example.deliveryproject.repository.UserRepository;
import com.example.deliveryproject.repository.DeliveryTariffRepository;
import com.example.deliveryproject.repository.OfficeRepository;

import com.example.deliveryproject.service.PostingService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostingServiceImpl implements PostingService {
    private PostingRepository postingRepository;
    private UserRepository userRepository;
    private DeliveryTariffRepository deliveryTariffRepository;

    private OfficeRepository officeRepository;

    public PostingServiceImpl (PostingRepository postingRepository,
                               UserRepository userRepository,
                               DeliveryTariffRepository deliveryTariffRepository,
                               OfficeRepository officeRepository) {
        this.postingRepository = postingRepository;
        this.userRepository = userRepository;
        this.deliveryTariffRepository = deliveryTariffRepository;
        this.officeRepository = officeRepository;
    }

    @Override
    public void savePosting(PostingDto postingDto) {
        Posting posting = new Posting();
//        Порядковый номер постинга у этого челика
//        posting.setTariffName(postingDto.getTariffName());
        posting.setSenderEmail(postingDto.getSenderEmail());

        User sender = userRepository.findByEmail(postingDto.getSenderEmail());

        if(sender == null){
            sender = checkUserExist();
        }
        int senderPostingsCount = userRepository.getCountPostingsByUserID(sender.getId());
        String postingNumber = String.format("%d-%d", sender.getId(), senderPostingsCount+1);
        posting.setPostingNumber(postingNumber);

        posting.setSenderEmail(sender.getEmail());
        posting.setSenderID(sender.getId());

        User reciever = userRepository.findByEmail(postingDto.getRecieverEmail());

        if(reciever == null){
            reciever = checkUserExist();
        }
        posting.setRecieverEmail(reciever.getEmail());
        posting.setRecieverID(reciever.getId());

        DeliveryTariff deliveryTariff = deliveryTariffRepository.findByTariffName(postingDto.getTariffName());
        if(deliveryTariff == null){
            deliveryTariff = checkDeliveryTariffExist();
        }
        posting.setDeliveryTariff(deliveryTariff);

        posting.setOfficeFromID(postingDto.getOfficeFromID());
        posting.setOfficeToID(postingDto.getOfficeToID());
        posting.setOfficeToID(postingDto.getOfficeToID());
        posting.setStatus("Новый");
        postingRepository.save(posting);
    }

    @Override
    public Posting findByPostingNumber(String postingNumber) {
        return postingRepository.getById(postingRepository.findIdByPostingNumber(postingNumber));
    }
    @Override
    public Posting getPostingByPostingNumber(String postingNumber) {
        return postingRepository.findById(postingRepository.findIdByPostingNumber(postingNumber)).get();
    }

    @Override
    public Posting updatePosting(Posting posting) {
        return postingRepository.save(posting);
    }

    @Override
    public void deletePostingByPostingNumber(String postingNumber) {
        postingRepository.deleteById(postingRepository.findIdByPostingNumber(postingNumber));
    }

//    @Override
//    public DeliveryTariff findByTariffName(String tariffName) {
//        return deliveryTariffRepository.findByTariffName(tariffName);
//    }

    @Override
    public List<PostingDto> findAllPostings() {
        List<Posting> postings = postingRepository.findAll();
        return postings.stream().map((posting) -> convertEntityToDto(posting))
                .collect(Collectors.toList());
    }

    @Override
    public Posting findById(Long id) {return postingRepository.getById(id);
    }

    private PostingDto convertEntityToDto(Posting posting){
        PostingDto postingDto = new PostingDto();
        postingDto.setPostingNumber(posting.getPostingNumber());

        postingDto.setTariffID(posting.getDeliveryTariff().getId());
        postingDto.setTariffName(posting.getDeliveryTariff().getTariffName());

        postingDto.setSenderEmail(posting.getSenderEmail());
        postingDto.setSenderID(posting.getSenderID());
        postingDto.setRecieverEmail(posting.getRecieverEmail());
        postingDto.setRecieverID(posting.getRecieverID());
        postingDto.setStatus(posting.getStatus());
        return postingDto;
    }

    private User checkUserExist() {
        User user = new User();
//        user.setName("ROLE_ADMIN");
        return userRepository.save(user);
    }

    private DeliveryTariff checkDeliveryTariffExist() {
        DeliveryTariff deliveryTariff = new DeliveryTariff();
//        user.setName("ROLE_ADMIN");
        return deliveryTariffRepository.save(deliveryTariff);
    }


}
