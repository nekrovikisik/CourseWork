package com.example.deliveryproject.repository;

import com.example.deliveryproject.entity.Office;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OfficeRepository extends JpaRepository<Office, Long> {
    Office findByFullOfficeName(String fullOfficeName);

    // TODO: 07.05.2023 сделать фильтрацию по региону (вроде сделала)
    List<Office> findAllByRegion(String region);

    @Query(value = "SELECT distinct region FROM offices order by region", nativeQuery = true)
    List<String> findAllRegions();
    @Query(value = "SELECT full_office_name FROM offices order by full_office_name", nativeQuery = true)
    List<String> findAllOfficeNames();


}
