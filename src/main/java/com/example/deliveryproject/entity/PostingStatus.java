package com.example.deliveryproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="posting_statuses")
public class PostingStatus {

    @Id
    private Long id;

    @Column(nullable=false)
    private String statusType; // прямой поток или обратный

    @Column(nullable=false)
    private String statusName;

    @Column(nullable=false)
    private String systemStatusName;

    @Column()
    @CreationTimestamp
    private Date createdAt;

    @Column()
    @UpdateTimestamp
    private Date modifyAt;

    @OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    private List<PostingEvent> postingEvents;

//    @ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
//    private Office office;


//    @ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
//    @JoinTable(
//            name = "posting_posting_statuses",
//            joinColumns = @JoinColumn(name = "posting_status_id", referencedColumnName="ID"),
//            inverseJoinColumns = @JoinColumn(name = "posting_id", referencedColumnName="ID")
//    )
//    private List<Posting> postings = new ArrayList<>();

}
