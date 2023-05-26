package com.example.deliveryproject.component;

import com.example.deliveryproject.entity.*;
import com.example.deliveryproject.repository.*;
import com.zaxxer.hikari.pool.HikariProxyCallableStatement;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Date;

@Component
public class PostingLoader {
    private final PostingRepository postingRepository;
    private final DeliveryTariffRepository deliveryTariffRepository;
    private final UserRepository userRepository;
    private final OfficeRepository officeRepository;
    private final PostingStatusRepository postingStatusRepository;


    @Autowired

    public PostingLoader(PostingRepository postingRepository,
                         DeliveryTariffRepository deliveryTariffRepository,
                         UserRepository userRepository,
                         OfficeRepository officeRepository,
                         PostingStatusRepository postingStatusRepository) {
        this.postingRepository = postingRepository;
        this.deliveryTariffRepository = deliveryTariffRepository;
        this.userRepository = userRepository;
        this.officeRepository = officeRepository;
        this.postingStatusRepository = postingStatusRepository;
    }
//    @PostConstruct
    public void loadPostings() throws IOException {
        //если таблица пустая, вставляем ПВЗ
        for(int i=0; i < 100; i++){
            Posting posting = createRandomPosting();
            postingRepository.save(posting);

        }
    }


    public Posting createRandomPosting() {
        Posting posting = new Posting();
        Random random = new Random();
        double probDeliveryTariff = random.nextDouble();
        if (probDeliveryTariff < 0.6) {
            posting.setDeliveryTariff(deliveryTariffRepository.findByTariffName("Эконом"));
        }
        else if (probDeliveryTariff < 0.85){
            posting.setDeliveryTariff(deliveryTariffRepository.findByTariffName("Экспресс"));
        }
        else {
            posting.setDeliveryTariff(deliveryTariffRepository.findByTariffName("Суперэкспресс"));
        }

        Long randomSenderId = random.nextLong(userRepository.count()) + 1;
        Long randomReceiverId = random.nextLong(userRepository.count()) + 1;
        while (randomSenderId.equals(randomReceiverId)){
            randomReceiverId = random.nextLong(userRepository.count()) + 1;
        }
        posting.setSender(userRepository.findById(randomSenderId).get());
        posting.setReceiver(userRepository.findById(randomReceiverId).get());

        int senderPostingsCount = postingRepository.countPostingBySender_Id(randomSenderId);
        String postingNumber = String.format("%d-%d", randomSenderId, senderPostingsCount+1);
        posting.setPostingNumber(postingNumber);

        Long randomOfficeFromId = random.nextLong(officeRepository.count()) + 1;
        Long randomOfficeToId = random.nextLong(officeRepository.count()) + 1;
        while (randomOfficeFromId.equals(randomOfficeToId)){
            randomOfficeToId = random.nextLong(officeRepository.count()) + 1;
        }
        posting.setOfficeFrom(officeRepository.findById(randomOfficeFromId).get());
        posting.setOfficeTo(officeRepository.findById(randomOfficeToId).get());


        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Long randomCreatedAt = timestamp.getTime() - random.nextLong(365)*1000*60*60*24;
        Timestamp postingCreatedAt = new Timestamp(randomCreatedAt);
        posting.setCreatedAt(postingCreatedAt);

        double currentStatusTypeProb = random.nextDouble();
        List<PostingStatus> statuses = postingStatusRepository.findAll();
        int currentStatusNum;
        long currentStateID;
        if (currentStatusTypeProb < 0.9) {
            // Прямой поток
            currentStatusNum = random.nextInt(9);
            currentStateID = Long.valueOf(1000 + currentStatusNum*10);
        }
        else {
            // Обратный поток
            currentStatusNum = random.nextInt(8);
            currentStateID = Long.valueOf(3000 + currentStatusNum*10);
        }

        List <PostingEvent> postingEvents = new ArrayList<PostingEvent>();;
        Timestamp previousStateCreatedAt = postingCreatedAt;

        for(PostingStatus status : statuses) {
            if (status.getId() <= currentStateID){
                PostingEvent postingEvent = new PostingEvent();
                postingEvent.setPostingNumber(postingNumber);
                postingEvent.setPostingStatus(status);
                // Рандомная дата ивента рассчитывается как дата предыдущего ивета + рандомные число дней от 0 до 2
                Long randomEventCreatedAt = previousStateCreatedAt.getTime() + (long) (random.nextDouble()*2*1000*60*60*24);
                postingEvent.setCreatedAt(new Timestamp(randomEventCreatedAt));
                postingEvent.setPostingStatus(status);

                previousStateCreatedAt = new Timestamp(randomEventCreatedAt);
                postingEvent.setPosting(posting);
                postingEvents.add(postingEvent);
            }
        }

        posting.setPostingEvents(postingEvents);



        return posting;
    }
}
