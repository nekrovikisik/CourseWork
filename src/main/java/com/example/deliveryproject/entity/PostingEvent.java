package com.example.deliveryproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
@Getter
@Setter
//@NoArgsConstructor
//@AllArgsConstructor
@Entity
@Table(name = "posting_events")
public class PostingEvent {
    @Id
    @GeneratedValue
    private Long id;

    @Column()
    private Date createdAt;

    @Column()
    private String postingNumber;

    @Transient
    private boolean autoFillCreatedAt = true;
    public void setAutoFillCreatedAt(boolean autoFillCreatedAt) {
        this.autoFillCreatedAt = autoFillCreatedAt;
        if (autoFillCreatedAt && createdAt == null) {
            createdAt = new Date();
        }
    }

    @Column()
    @UpdateTimestamp
    private Date modifyAt;

    @ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    private Warehouse warehouse;

    @ManyToOne
    @JoinColumn(name = "posting_id")
    private Posting posting;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private PostingStatus postingStatus;


    }
