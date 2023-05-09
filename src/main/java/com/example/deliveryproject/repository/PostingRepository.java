package com.example.deliveryproject.repository;

import com.example.deliveryproject.entity.Posting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface PostingRepository extends JpaRepository<Posting, Long> {
    Posting findByPostingNumber(String postingNumber);

    @Query(nativeQuery = true, value = "SELECT max(id) FROM postings WHERE posting_number = :postingNumber LIMIT 1")
    Long findIdByPostingNumber(@Param("postingNumber") String postingNumber);

//    @Query(nativeQuery = true, value = "select * from postings where senderID = :senderID LIMIT 1")
    List<Posting> findAllBySenderID(Long id);
    List<Posting> findAllByRecieverID(Long id);
}
