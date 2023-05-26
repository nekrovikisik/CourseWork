package com.example.deliveryproject.repository;

import com.example.deliveryproject.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
    Warehouse findByFullWarehouseName(String fullWarehouseName);

    List<Warehouse> findAllByRegion(String region);

}
