package com.aroha.validation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSummaryDTO {
	
	private Integer recordNo;
    private String firstName;
    private String lastName;
    private String dob;
    private String gender;
    private String city;
	
}
