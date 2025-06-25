package com.aroha.validation.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.aroha.validation.dto.UserSalaryProjection;
import com.aroha.validation.entity.User;
import com.aroha.validation.entity.UserId;

@Repository
public interface UserRepository extends JpaRepository<User, UserId> {

	// Native query to insert each record
	@Modifying
	@Transactional
	@Query(value = "INSERT INTO user(f_name,l_name,gender,date_of_birth,record_no,education,house_number,address1,address2,city,pincode,mobile_number,company,monthly_salary) VALUES(:fName,:lName,:gender,:dob,:recordNo,:education,:houseNumber,:address1,:address2,:city,:pincode,:mobileNumber,:company,:monthlySalary)", nativeQuery = true)
	void insertUser(@Param("fName") String fName, @Param("lName") String lName, @Param("gender") String gender,
			@Param("dob") LocalDate dateOfBirth, @Param("recordNo") int recordNo, @Param("education") String education,
			@Param("houseNumber") int houseNumber, @Param("address1") String address1,
			@Param("address2") String address2, @Param("city") String city, @Param("pincode") int pincode,
			@Param("mobileNumber") String mobileNumber, @Param("company") String company,
			@Param("monthlySalary") double monthlySalary);

	//Native Query to get by first name starts with and lastname starts with
	@Query(value = "SELECT record_no, f_name AS firstName, l_name AS lastName, date_of_birth AS dob, gender, city "
			+ "FROM user " + "WHERE f_name LIKE :fNameStartsWith AND l_name LIKE :lNameStartsWith", nativeQuery = true)
	List<Object[]> findByNamePrefixes(@Param("fNameStartsWith") String fNameStartsWith,
			@Param("lNameStartsWith") String lNameStartsWith);

	//JPQL query to get by birth date betwwn given inputs
	@Query("SELECT u FROM User u WHERE u.id.dateOfBirth BETWEEN :startDate AND :endDate")
	List<User> findUsersByDobBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

	//Native Query to find by monthly salary 
	@Query(value = "SELECT record_no AS recordNo, f_name AS firstName, l_name AS lastName, "
			+ "date_of_birth AS dateOfBirth, gender AS gender, city AS city, monthly_salary AS monthlySalary "
			+ "FROM user WHERE monthly_salary >= :monthlySalary", nativeQuery = true)
	List<UserSalaryProjection> findBySalary(@Param("monthlySalary") double monthlySalary);
}
