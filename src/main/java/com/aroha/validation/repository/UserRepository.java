package com.aroha.validation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.aroha.validation.entity.User;
import com.aroha.validation.entity.UserId;

@Repository
public interface UserRepository extends JpaRepository<User,UserId>{

	//Native query to insert each record
	@Modifying
	@Transactional
	@Query(value="INSERT INTO user(f_name,l_name,gender,date_of_birth,record_no,education,house_number,address1,address2,city,pincode,mobile_number,company,monthly_salary) VALUES(:lName,:lName,:gender,:dob,:recordNo,:education,:houseNumber,:address1,:address2,:city,:pincode,:mobileNumber,:company,:monthlySalary)",nativeQuery=true)
	void insertUser(@Param("fName") String fName,@Param("lName") String lName,@Param("gender") String gender, @Param("dob") String dateOfBirth, @Param("recordNo") int recordNo,@Param("education") String education,
			@Param("houseNumber") int houseNumber,@Param("address1") String address1,@Param("address2")  String address2,@Param("city")  String city, @Param("pincode") int pincode,@Param("mobileNumber")  String mobileNumber,
			@Param("company") String company,@Param("monthlySalary")  double monthlySalary);

}
