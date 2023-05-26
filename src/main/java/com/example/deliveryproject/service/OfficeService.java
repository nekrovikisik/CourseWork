package com.example.deliveryproject.service;

import com.example.deliveryproject.dto.OfficeDto;

import java.util.List;

public interface OfficeService {
    public List<OfficeDto> findOfficeDtoByRegion(String region);
    public List<OfficeDto> findAll();

    }
