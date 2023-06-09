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
@Table(name="offices")
public class Office
{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String region;

    @Column()
    private String municipality;

    @Column(nullable=false)
    private String settlement;

    @Column(nullable=false)
    private Double latitude;

    @Column(nullable=false)
    private Double longitude;

    @Column(nullable=false, unique=true)
    private String fullOfficeName;



    @OneToMany(mappedBy = "officeFrom")
    private List<Posting> postingFrom = new ArrayList<>();
    @OneToMany(mappedBy = "officeTo")
    private List<Posting> postingTo = new ArrayList<>();

}
