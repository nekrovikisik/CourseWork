package com.example.deliveryproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.CreationTimestamp;


import java.util.Comparator;
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

    @Column()
    private Date createdAt;
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

    @ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.MERGE)
    private DeliveryTariff deliveryTariff = new DeliveryTariff();

    @ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.MERGE)
    private User sender = new User();

    @ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.MERGE)
    private User receiver = new User();

    @ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.MERGE)
    private Office officeFrom = new Office();

    @ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.MERGE)
    private Office officeTo = new Office();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<PostingEvent> postingEvents;

    public PostingEvent getLastEvent(){
        PostingEvent lastEvent = postingEvents.stream()
            .max(Comparator.comparing(PostingEvent::getCreatedAt)).orElse(null);
        return lastEvent;
    }
//
//    @ManyToMany(mappedBy = "postings")
//    private List<PostingStatus> postingStatuses;


//    private Queue<PostingStatus> postingStatuses = new LinkedList<PostingStatus>();
}
