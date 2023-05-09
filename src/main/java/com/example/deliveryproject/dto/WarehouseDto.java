package com.example.deliveryproject.dto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class WarehouseDto {

    private Long id;
    @NotEmpty
    private String region;
    @NotEmpty
    private String settlement;
    @NotNull
    private Double latitude;
    @NotNull
    private Double longitude;
    @NotEmpty
    private String fullWarehouseName;

}
