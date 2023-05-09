package com.example.deliveryproject.repository;

import com.example.deliveryproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
//import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
//    select count(*) from postings where senderID=1;
    @Query(nativeQuery = true, value = "select count(*) from postings where senderID = :senderID LIMIT 1")
    int getCountPostingsByUserID(@Param("senderID") Long senderID);

}
