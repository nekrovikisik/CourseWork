package com.example.deliveryproject.component;
import com.example.deliveryproject.entity.DeliveryTariff;
import com.example.deliveryproject.repository.DeliveryTariffRepository;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeliveryTariffLoader {

    private final DeliveryTariffRepository deliveryTariffRepository;

    @Autowired
    public DeliveryTariffLoader(DeliveryTariffRepository deliveryTariffRepository) {
        this.deliveryTariffRepository = deliveryTariffRepository;
    }
    @PostConstruct
    public void loadInitDeliveryTariffs() {
        //если таблица пустая, вставляем тарифы
        if (deliveryTariffRepository.count() == 0) {
            DeliveryTariff baseTariff = new DeliveryTariff();
            baseTariff.setTariffName("Эконом");
            baseTariff.setPlanDayMin(7);
            baseTariff.setPlanDayMax(10);
            baseTariff.setBasePrice(50);

            DeliveryTariff expTariff = new DeliveryTariff();
            expTariff.setTariffName("Экспресс");
            expTariff.setPlanDayMin(5);
            expTariff.setPlanDayMax(8);
            expTariff.setBasePrice(80);

            DeliveryTariff superExpTariff = new DeliveryTariff();
            superExpTariff.setTariffName("Суперэкспресс");
            superExpTariff.setPlanDayMin(5);
            superExpTariff.setPlanDayMax(8);
            superExpTariff.setBasePrice(80);

            deliveryTariffRepository.save(baseTariff);
            deliveryTariffRepository.save(expTariff);
            deliveryTariffRepository.save(superExpTariff);
        }
    }
}

