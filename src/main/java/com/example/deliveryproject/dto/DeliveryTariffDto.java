package com.example.deliveryproject.dto;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryTariffDto {
    private Long id;

    @NotEmpty
    private String tariffName;

//    @NotNull
    private int planDayMin;

//    @NotNull
    private int planDayMax;

//    @NotNull
    private int basePrice;

}
