package com.example.deliveryproject.service.impl;

import com.example.deliveryproject.dto.PostingDto;
import com.example.deliveryproject.entity.*;

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

        User sender = userRepository.findByEmail(postingDto.getSenderEmail());

        if(sender != null){
            posting.setSender(sender);
        }

        int senderPostingsCount = postingRepository.countPostingBySender_Id(sender.getId());
        String postingNumber = String.format("%d-%d", sender.getId(), senderPostingsCount+1);
        posting.setPostingNumber(postingNumber);

        User receiver = userRepository.findByEmail(postingDto.getReceiverEmail());
        if(receiver != null){
            posting.setReceiver(receiver);
        }


        DeliveryTariff deliveryTariff = deliveryTariffRepository.findByTariffName(postingDto.getTariffName());
        if(deliveryTariff != null){
            posting.setDeliveryTariff(deliveryTariff);
        }

        Office officeFrom = officeRepository.findByFullOfficeName(postingDto.getOfficeFromName());
        if(officeFrom != null){
            posting.setOfficeFrom(officeFrom);
        }

        Office officeTo = officeRepository.findByFullOfficeName(postingDto.getOfficeToName());
        if(officeTo != null){
            posting.setOfficeTo(officeTo);
        }

//        posting.setStatus("Новый");
        postingRepository.save(posting);
    }

    @Override
    public Posting findByPostingNumber(String postingNumber) {
        return postingRepository.getById(postingRepository.findIdByPostingNumber(postingNumber));
    }
    @Override
    public PostingDto findPostingDTOByPostingNumber(String postingNumber) {
        return convertEntityToDto(postingRepository.findById(postingRepository.findIdByPostingNumber(postingNumber)).get());
    }

    @Override
    public Posting updatePosting(Posting posting) {
        return postingRepository.save(posting);
    }

    @Override
    public boolean deletePostingByPostingNumber(String postingNumber) {
        long id = postingRepository.findIdByPostingNumber(postingNumber);
        if (postingRepository.existsById(id)) {
            postingRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<PostingDto> findPostingsBySenderID(Long senderID){
        List<Posting> postings = postingRepository.findAllBySender_Id(senderID);
        return postings.stream().map((posting) -> convertEntityToDto(posting))
                .collect(Collectors.toList());
    }
    @Override
    public List<PostingDto> findPostingsByReceiverID(Long receiverID){
        List<Posting> postings = postingRepository.findAllByReceiver_Id(receiverID);
        return postings.stream().map((posting) -> convertEntityToDto(posting))
                .collect(Collectors.toList());
    }

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

        postingDto.setSenderEmail(posting.getSender().getEmail());
        postingDto.setSenderID(posting.getSender().getId());
        postingDto.setSenderName(posting.getSender().getName());

        postingDto.setReceiverEmail(posting.getReceiver().getEmail());
        postingDto.setReceiverID(posting.getReceiver().getId());
        postingDto.setReceiverName(posting.getReceiver().getName());

        postingDto.setOfficeFromID(posting.getOfficeFrom().getId());
        postingDto.setOfficeFromName(posting.getOfficeFrom().getFullOfficeName());
        postingDto.setOfficeFromLat(posting.getOfficeFrom().getLatitude());
        postingDto.setOfficeFromLon(posting.getOfficeFrom().getLongitude());

        postingDto.setOfficeToID(posting.getOfficeTo().getId());
        postingDto.setOfficeToName(posting.getOfficeTo().getFullOfficeName());
        postingDto.setOfficeToLat(posting.getOfficeTo().getLatitude());
        postingDto.setOfficeToLon(posting.getOfficeTo().getLongitude());

        postingDto.setCreatedAt(posting.getCreatedAt());
        PostingEvent lastEvent = posting.getLastEvent();
        if(lastEvent != null){
            postingDto.setModifyAt(posting.getLastEvent().getCreatedAt());
            postingDto.setStatus(posting.getLastEvent().getPostingStatus().getStatusName());
        }
        else {
            postingDto.setModifyAt(posting.getCreatedAt());
            postingDto.setStatus("Создан");
        }

        return postingDto;
    }

}
