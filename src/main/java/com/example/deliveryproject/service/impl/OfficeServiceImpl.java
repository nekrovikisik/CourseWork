package com.example.deliveryproject.service.impl;

import com.example.deliveryproject.dto.OfficeDto;
import com.example.deliveryproject.dto.PostingEventDto;
import com.example.deliveryproject.entity.Office;
import com.example.deliveryproject.entity.PostingEvent;
import com.example.deliveryproject.repository.*;
import com.example.deliveryproject.service.OfficeService;
import com.example.deliveryproject.service.PostingEventService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OfficeServiceImpl implements OfficeService {
    private OfficeRepository officeRepository;
    public OfficeServiceImpl(OfficeRepository officeRepository) {
        this.officeRepository = officeRepository;
    }

    @Override
    public List<OfficeDto> findOfficeDtoByRegion(String region) {
        return officeRepository.findAllByRegion(region).stream().map((office) -> convertEntityToDto(office))
                .collect(Collectors.toList());
    }
    @Override
    public List<OfficeDto> findAll() {
        return officeRepository.findAll().stream().map((office) -> convertEntityToDto(office))
                .collect(Collectors.toList());
    }
    private OfficeDto convertEntityToDto(Office office) {
        OfficeDto officeDto = new OfficeDto();
        officeDto.setFullOfficeName(office.getFullOfficeName());
        officeDto.setMunicipality(office.getMunicipality());
        officeDto.setSettlement(office.getSettlement());
        officeDto.setRegion(office.getRegion());
        officeDto.setLatitude(office.getLatitude());
        officeDto.setLongitude(office.getLongitude());

        return officeDto;
    }
}
