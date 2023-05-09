package com.example.deliveryproject.repository;

import com.example.deliveryproject.entity.Posting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostingRepository extends JpaRepository<Posting, Long> {
    Posting findByPostingNumber(String postingNumber);

//    Long findIdByPostingNumber(String postingNumber);

//    @Query(nativeQuery = true, value = "SELECT * FROM SLSNotification s WHERE s.userId = :userId ORDER BY snumber DESC LIMIT 20")
//    List<SLSNotification> getUserIdforManage(@Param("userId") String userId);

    @Query(nativeQuery = true, value = "SELECT max(id) FROM postings WHERE posting_number = :postingNumber LIMIT 1")
    Long findIdByPostingNumber(@Param("postingNumber") String postingNumber);

}
