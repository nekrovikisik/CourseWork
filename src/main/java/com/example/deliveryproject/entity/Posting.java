package com.example.deliveryproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="postings")
public class Posting
{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true)
    private String postingNumber;

//    private DeliveryTariff deliveryTariff;
//    @Column(nullable=false) // перенесла юник на постингнамбер, так что может будет выебываться при запуске
//    private String tariffName;

    @Column(nullable=false)
    private String senderEmail;

    @Column(nullable=false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long senderID;

    @Column(nullable=false)
    private String recieverEmail;

    @Column(nullable=false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recieverID;

    @Column(nullable=true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long officeFromID;

    @Column(nullable=true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long officeToID;

    @Column(nullable=true)
    private String status;

    @Column()
    @CreationTimestamp
    private Date createdAt;
    @Column()
    @UpdateTimestamp
    private Date modifyAt;

//   Корректнее было бы использовать ManyToOne

    @ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
//    @ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinTable(
            name="tie_postings_senders",
            joinColumns={@JoinColumn(name="SENDER_ID", referencedColumnName="ID")},
            inverseJoinColumns={@JoinColumn(name="USER_ID", referencedColumnName="ID")})
    private List<User> users = new ArrayList<>();
//    private User user = new User();

    @ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
//    @JoinTable(
//            name="postings_deliverytariffs",
//            joinColumns={@JoinColumn(name="PostingId", referencedColumnName="ID")},
//            inverseJoinColumns={@JoinColumn(name="DeliveryTariffId", referencedColumnName="ID")})
    private DeliveryTariff deliveryTariff = new DeliveryTariff();

}
