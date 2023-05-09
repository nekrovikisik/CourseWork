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

    @Column(nullable=true)
    private String status;

    @Column()
    @CreationTimestamp
    private Date createdAt;
    @Column()
    @UpdateTimestamp
    private Date modifyAt;

    @ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinTable(
            name="tie_postings_senders",
            joinColumns={@JoinColumn(name="SENDER_ID", referencedColumnName="ID")},
            inverseJoinColumns={@JoinColumn(name="USER_ID", referencedColumnName="ID")})
    private List<User> users = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    private DeliveryTariff deliveryTariff = new DeliveryTariff();

    @ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    private User sender = new User();

    @ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    private User receiver = new User();

    @ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    private Office officeFrom = new Office();

    @ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    private Office officeTo = new Office();

}
