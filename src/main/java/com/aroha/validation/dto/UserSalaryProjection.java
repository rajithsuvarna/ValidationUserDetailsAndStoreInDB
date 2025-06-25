package com.aroha.validation.dto;

import java.time.LocalDate;

public interface UserSalaryProjection {
    Integer getRecordNo();      // for record_no
    String getFirstName();      // for f_name
    String getLastName();       // for l_name
    LocalDate getDateOfBirth(); // for date_of_birth
    String getGender();         // for gender
    String getCity();           // for city
    Double getMonthlySalary();  // for monthly_salary
}
