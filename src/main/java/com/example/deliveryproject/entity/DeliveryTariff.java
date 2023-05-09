package com.example.deliveryproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="deliverytariffs")
public class DeliveryTariff
{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true)
    private String tariffName;

    @Column(nullable=false)
    private int planDayMin;

    @Column(nullable=false)
    private int planDayMax;

    @Column(nullable=false)
    private int basePrice;


//    @OneToMany(fetch = FetchType.EAGER, mappedBy = "deliveryTariff")
    @OneToMany(mappedBy = "deliveryTariff")
    private List<Posting> postings = new ArrayList<>();

}
