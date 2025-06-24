package com.aroha.validation.service;

import org.springframework.web.multipart.MultipartFile;

import com.aroha.validation.dto.UserResponse;

public interface UserService {

	UserResponse getValidated(MultipartFile file);

}
