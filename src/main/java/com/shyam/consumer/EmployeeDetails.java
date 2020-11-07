package com.shyam.consumer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeDetails {
    private String employeeId;
    private String employeeName;
    private String street;
    private String city;
    private String state;
    private String zipCode;
}
