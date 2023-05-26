package com.example.deliveryproject.component;
import com.example.deliveryproject.entity.PostingStatus;
import com.example.deliveryproject.repository.PostingStatusRepository;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PostingStatusLoader {

    private final PostingStatusRepository PostingStatusRepository;

    @Autowired
    public PostingStatusLoader(PostingStatusRepository PostingStatusRepository) {
        this.PostingStatusRepository = PostingStatusRepository;
    }
    @PostConstruct
    public void loadInitPostingStatuss() {
        //если таблица пустая, вставляем тарифы
        if (PostingStatusRepository.count() == 0) {
            createPostingStatus(1000L, "order", "posting_created", "Создан");
            createPostingStatus(1010L, "order", "posting_packing", "На упаковке");
            createPostingStatus(1020L, "order", "posting_transferring_to_delivery", "Передается в доставку");
            createPostingStatus(1030L, "order", "posting_in_carriage", "В перевозке");
            createPostingStatus(1040L, "order", "posting_in_sort_center", "В сортировочном центре");
            createPostingStatus(1050L, "order", "posting_on_way_to_city", "На пути в ваш город");
            createPostingStatus(1060L, "order", "posting_on_way_to_pickup_point", "На пути в пункт выдачи");
            createPostingStatus(1070L, "order", "posting_in_pickup_point", "Уже в пункте выдачи");
            createPostingStatus(1080L, "order", "posting_done", "Получен");
            createPostingStatus(3010L, "return", "return_created", "Создана заявка на возврат");
            createPostingStatus(3020L, "return", "return_transferring_to_delivery", "Передается в доставку");
            createPostingStatus(3030L, "return", "return_in_carriage", "В перевозке");
            createPostingStatus(3040L, "return", "return_in_sort_center", "В сортировочном центре");
            createPostingStatus(3050L, "return", "return_on_way_to_city", "На пути в город отправителя");
            createPostingStatus(3060L, "return", "return_on_way_to_pickup_point", "На пути в пункт выдачи отправителя");
            createPostingStatus(3070L, "return", "return_in_pickup_point", "Уже в пункте выдачи отправителя");
            createPostingStatus(3080L, "return", "return_done", "Получен отправителем");
            createPostingStatus(3090L, "return", "return_annulled", "Утилизирован");
        }
    }

    public void createPostingStatus(Long id, String statusType, String systemStatusName, String statusName){
        PostingStatus postingStatus = new PostingStatus();
        postingStatus.setId(id); // or return
        postingStatus.setStatusType(statusType); // or return
        postingStatus.setSystemStatusName(systemStatusName);
        postingStatus.setStatusName(statusName);
        PostingStatusRepository.save(postingStatus);
    }
}

