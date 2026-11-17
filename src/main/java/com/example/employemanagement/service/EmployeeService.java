package com.example.employemanagement.service;


import com.example.employemanagement.model.EmployeeRequest;
import com.example.employemanagement.model.EmployeeResponse;

import java.util.List;

public interface EmployeeService {

    EmployeeResponse createEmployee(EmployeeRequest request);

    List<EmployeeResponse> getAllEmployees();

    EmployeeResponse getEmployeeById(Long id);

    EmployeeResponse updateEmployee(
            Long id, EmployeeRequest request);

    void deleteEmployee(Long id);
}
