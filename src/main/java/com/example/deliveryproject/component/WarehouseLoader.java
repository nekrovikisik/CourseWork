package com.example.deliveryproject.component;

import com.example.deliveryproject.entity.Warehouse;
import com.example.deliveryproject.repository.WarehouseRepository;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


@Component
public class WarehouseLoader {
    private final WarehouseRepository warehouseRepository;
    private final char CSV_COLUMN_SEPARATOR = ';';


    @Autowired
    public WarehouseLoader(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    @PostConstruct
    public void loadInitWarehouses() throws IOException {
        //если таблица пустая, вставляем ПВЗ
        if (warehouseRepository.count() == 0) {
            String filename = "src/main/resources/data/warehouse_df.csv";
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            CsvMapper mapper = new CsvMapper();
            CsvSchema schema = CsvSchema.emptySchema().withHeader().withColumnSeparator(CSV_COLUMN_SEPARATOR);
            MappingIterator<Warehouse> iterator = mapper.readerFor(Warehouse.class).with(schema).readValues(reader);

            while (iterator.hasNext()) {
                Warehouse warehouse = iterator.next();
                warehouseRepository.save(warehouse);
            }
        }
    }
}
