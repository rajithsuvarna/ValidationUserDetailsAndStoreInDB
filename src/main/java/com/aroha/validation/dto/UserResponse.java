package com.aroha.validation.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {

	private Long SuccessRecord;
	private Long FailureRecord;
	private Long Timetaken;
}
