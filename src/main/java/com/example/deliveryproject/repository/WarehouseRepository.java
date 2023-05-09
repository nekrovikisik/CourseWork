package com.example.deliveryproject.repository;

import com.example.deliveryproject.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
    Warehouse findByFullWarehouseName(String fullWarehouseName);

    // TODO: 07.05.2023 сделать фильтрацию по региону (вроде сделала)
    List<Warehouse> findAllByRegion(String region);

}
