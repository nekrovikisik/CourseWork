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
    private String senderName;
    @NotEmpty(message = "Enter receiver's email")
    private String receiverEmail;
    private Long receiverID;
    private String receiverName;

    private Long officeFromID;
    private String officeFromName;
    private double officeFromLat;
    private double officeFromLon;

    private Long officeToID;
    private String officeToName;
    private double officeToLat;
    private double officeToLon;

    private String status;
    private Date createdAt;
    private Date modifyAt;

}
