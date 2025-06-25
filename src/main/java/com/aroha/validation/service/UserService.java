package com.aroha.validation.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.aroha.validation.dto.UserResponse;
import com.aroha.validation.dto.UserSalaryProjection;
import com.aroha.validation.dto.UserSummaryDTO;
import com.aroha.validation.entity.User;

public interface UserService {

	UserResponse getValidated(MultipartFile file);

	List<UserSummaryDTO> getFilteredUsers(String firstname, String lastname);

	List<User> findUsersByDobBetween(String startDate, String endDate);

	List<UserSalaryProjection> findBySalary(double salary);

}
