package com.example.deliveryproject.component;

import com.example.deliveryproject.entity.Office;
import com.example.deliveryproject.repository.OfficeRepository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;


@Component
public class OfficeLoader {
    private final OfficeRepository officeRepository;
    private final char CSV_COLUMN_SEPARATOR = ';';


    @Autowired
    public OfficeLoader(OfficeRepository officeRepository) {
        this.officeRepository = officeRepository;
    }

    @PostConstruct
    public void loadInitOffices() throws IOException {
        //если таблица пустая, вставляем ПВЗ
        if (officeRepository.count() == 0) {
            String filename = "src/main/resources/data/pvz_df.csv";
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            CsvMapper mapper = new CsvMapper();
            CsvSchema schema = CsvSchema.emptySchema().withHeader().withColumnSeparator(CSV_COLUMN_SEPARATOR);
            MappingIterator<Office> iterator = mapper.readerFor(Office.class).with(schema).readValues(reader);

            while (iterator.hasNext()) {
                Office office = iterator.next();
                officeRepository.save(office);
            }
        }
    }
}
