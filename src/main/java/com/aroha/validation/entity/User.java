package com.aroha.validation.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @EmbeddedId
    private UserId id;
    
    private int recordNo; 
    private String education;
    private int houseNumber;
    private String address1;
    private String address2;
    private String city;
    private int pincode;
    private String mobileNumber;
    private String company;
    private double monthlySalary;
}