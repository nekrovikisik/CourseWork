package com.example.deliveryproject.dto;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostingDto
{
    private Long id;
//    @NotEmpty/**/
    private String postingNumber;
    @NotEmpty(message = "Choose the tariff")
    private String tariffName;
    private long tariffID;
    @NotEmpty(message = "Enter sender's email")
    private String senderEmail;
    private Long senderID;
    @NotEmpty(message = "Enter reciever's email")
    private String recieverEmail;
    private Long recieverID;
    private Long officeFromID;
    private Long officeToID;
    private String status;
    private Date createdAt;
    private Date modifyAt;

}
