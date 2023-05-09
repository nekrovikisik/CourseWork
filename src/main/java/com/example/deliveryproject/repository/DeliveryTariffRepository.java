package com.example.deliveryproject.repository;

import com.example.deliveryproject.entity.DeliveryTariff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface DeliveryTariffRepository extends JpaRepository<DeliveryTariff, Long> {
    DeliveryTariff findByTariffName(String tariffName);

    @Query(value = "SELECT tariff_name FROM deliverytariffs order by id", nativeQuery = true)
    List<String> findAllTariffNames();

}
