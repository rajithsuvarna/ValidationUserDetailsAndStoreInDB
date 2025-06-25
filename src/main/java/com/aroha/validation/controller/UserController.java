package com.aroha.validation.controller;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.aroha.validation.dto.UserResponse;
import com.aroha.validation.dto.UserSalaryProjection;
import com.aroha.validation.dto.UserSummaryDTO;
import com.aroha.validation.entity.User;
import com.aroha.validation.service.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/users")
@Slf4j
public class UserController {

private UserService userService;
	
	//Constructor injection
	public UserController(UserService userService) {
		this.userService=userService;
	}
	
	
	//To validate csv file records and store in db
	@PostMapping("/validate")
	public ResponseEntity<UserResponse> getValidated(@RequestParam("file") MultipartFile file) {
		
		//Strat time
		LocalDateTime startTime=LocalDateTime.now();
		
		//Calling service layer methd to validate csv file records
		UserResponse userResponse=userService.getValidated(file);

		//Capturing end time
		LocalDateTime endTime=LocalDateTime.now();
		
		log.info("StartTime :{}",startTime);
		log.info("EndTime :{}",endTime);
		
		//Finding the time taken difference using starttime and endtime
		Duration duration=Duration.between(startTime, endTime);
		userResponse.setTimetaken(duration.toMillis());
		
		log.info("TimeTaken :{}",duration.toMillis());
		return ResponseEntity.ok(userResponse);
	}
	
	//Get by first name and last name starts with given parameter
	@GetMapping("/byname")
	public ResponseEntity<List<UserSummaryDTO>> getFilteredUsers(@RequestParam String firstname,@RequestParam String lastname) {
		List<UserSummaryDTO> resultlist=userService.getFilteredUsers(firstname,lastname);
		return ResponseEntity.ok(resultlist);
		
	}
	
	//Get by date in between given parameter
	@GetMapping("/bydate")
	public ResponseEntity<List<User>> findUsersByDobBetween(@RequestParam String startDate,@RequestParam String endDate){
		List<User> user=userService.findUsersByDobBetween(startDate,endDate);
		return ResponseEntity.ok(user);
	}
	
	//Get by salary greater than passed parameter salary
	@GetMapping("/salary")
	public List<UserSalaryProjection> getUsersWithSalary(@RequestParam double salary) {
	    return userService.findBySalary(salary);
	}
}
