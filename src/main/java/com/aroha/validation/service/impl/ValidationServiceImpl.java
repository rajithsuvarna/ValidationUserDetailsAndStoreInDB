package com.aroha.validation.service.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.aroha.validation.dto.UserResponse;
import com.aroha.validation.entity.User;
import com.aroha.validation.entity.UserId;
import com.aroha.validation.repository.UserRepository;
import com.aroha.validation.service.UserService;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ValidationServiceImpl implements UserService {

	private UserRepository userRepository;

	//Constructor injection
	public ValidationServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	// method to validate the userdetails
	@Override
	public UserResponse getValidated(MultipartFile file) {

		// For counting records
		Long successRecords = 0L;
		Long failedRecords = 0L;

		//try with resource to read CSV file
		try (CSVReader reader = new CSVReader(new java.io.InputStreamReader(file.getInputStream()));) {
			
			//Array of string to store fields of each line
			String[] fieldList;
			
			//extracting headerline and checking whether it is null or not
			String[] headerLine = reader.readNext();

			if (headerLine == null) {
				throw new RuntimeException("Uploaded file is empty.");
			}

			//Set to validate duplicate records
			Set<String> existingUser = new HashSet<>();

			//Iterating over each Line
			while ((fieldList = reader.readNext()) != null) {
				
				//Validation-1: Unique records
				//Generating one unique string and storing that in the set, While using it will give true or false
				String unique = fieldList[1] + fieldList[2] + fieldList[3] + fieldList[4];

				if (!existingUser.add(unique)) {
					failedRecords++;
					continue;
				}
				
				//Validation-2: All field are Mandatory
				//Checking whether the fields are empty or blank
				boolean isEmptyORBlank = false;
				for (int i = 0; i < 14; i++) {
					if (fieldList[i].isEmpty() || fieldList[i].isBlank()) {
						isEmptyORBlank = true;
						break;
					}
				}
				if (isEmptyORBlank) {
					failedRecords++;
					continue;
				}

				//Validation-3: Mobile number check
				if (!Pattern.matches("[789]{1}\\d{9}", fieldList[11])) {
					failedRecords++;
					continue;
				}

				//Validation-4: Date of Birth Validation
				try {
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
					LocalDate dob = LocalDate.parse(fieldList[3], formatter);
					LocalDate today = LocalDate.now();
					Period age = Period.between(dob, today);

					//Checking date of birth is not greater than todays date, and age is not greater than 100
					if (dob.isAfter(today) || age.getYears() > 100) {
						failedRecords++;
						continue;
					}	
				//If we get any exception related to dat time format or while parsing date time from string to LocalDateTime
				} catch (DateTimeParseException e) {
					failedRecords++;
					continue;
				}

				// Clean up address fields
				fieldList[7] = fieldList[7].replaceAll("[^a-zA-Z0-9\\s,]", "");
				fieldList[8] = fieldList[8].replaceAll("[^a-zA-Z0-9\\s,]", "");

				successRecords++;

				try {
					// inserting each user fields to the DB
					userRepository.insertUser(fieldList[1], // fName
							fieldList[2], // lName
							fieldList[4], // gender
							fieldList[3], // dateOfBirth
							Integer.parseInt(fieldList[0]), // recordNo
							fieldList[5], // education
							Integer.parseInt(fieldList[6]), // houseNumber
							fieldList[7], // address1
							fieldList[8], // address2
							fieldList[9], // city
							Integer.parseInt(fieldList[10]), // pincode
							fieldList[11], // mobileNumber
							fieldList[12], // company
							Double.parseDouble(fieldList[13]) // monthlySalary
					);
				} catch (DataIntegrityViolationException ex) {
					// Duplicate or constraint violation
					log.warn("Duplicate or failed record");
				}
				
			}
			log.info("Failed Records :{}", failedRecords);
			log.info("Success Records :{}", successRecords);

			//returning User response object with failed and success record count
			return UserResponse.builder().FailureRecord(failedRecords).SuccessRecord(successRecords).build();

		} catch (IOException | CsvValidationException e) {
			log.debug("Failed to process file");
		}
	}

}
