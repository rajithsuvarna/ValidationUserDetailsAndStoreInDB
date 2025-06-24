package com.aroha.validation.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserId implements Serializable {
	
	@Column(length = 50)
    private String fName;
	
	@Column(length = 50)
    private String lName;
	
	@Column(length = 10)
    private String gender;
	
	@Column(length = 20)
    private String dateOfBirth; 
}