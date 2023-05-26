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
@Table(name="warehouses")
public class Warehouse
{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String region;

    @Column(nullable=false)
    private String settlement;

    @Column(nullable=false)
    private Double latitude;

    @Column(nullable=false)
    private Double longitude;

    @Column(nullable=false, unique=true)
    private String fullWarehouseName;

    @OneToMany(mappedBy = "warehouse")
    private List<PostingEvent> postingEvents = new ArrayList<>();


}
