
package com.example.employemanagement.service;

import com.example.employemanagement.model.EmployeeRequest;
import com.example.employemanagement.model.EmployeeResponse;
import com.example.employemanagement.entity.Employee;
import com.example.employemanagement.exception.ResourceNotFoundException;
import com.example.employemanagement.repository.EmployeeRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // Convert Entity into Response DTO
    private EmployeeResponse mapToResponse(Employee employee) {

      var responseEmployee=   new EmployeeResponse(
                employee.getId(),
                employee.getName(),
                employee.getEmail(),
                employee.getDepartment(),
                employee.getSalary()
        );

        return responseEmployee;
    }

    // CREATE
    @Override
    public EmployeeResponse createEmployee(EmployeeRequest request) {

        if (employeeRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }

        Employee employee = new Employee(
                request.getName(),
                request.getEmail(),
                request.getDepartment(),
                request.getSalary()
        );

        Employee savedEmployee = employeeRepository.save(employee);

        return mapToResponse(savedEmployee);
    }

    // READ ALL
    @Override
    @Transactional(readOnly = true)
    public List<EmployeeResponse> getAllEmployees() {

        var result= employeeRepository.findAll();

        var response = result
                .stream()
                .map(this::mapToResponse)
                .toList();

        return response;
    }

    // READ BY ID
    @Override
    @Transactional(readOnly = true)
    public EmployeeResponse getEmployeeById(Long id) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee not found with ID: " + id
                        )
                );

        return mapToResponse(employee);
    }

    // UPDATE
    @Override
    public EmployeeResponse updateEmployee(Long id, EmployeeRequest request) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee not found with ID: " + id
                        )
                );

       /* employeeRepository.findByEmail(request.getEmail())
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new IllegalArgumentException(
                            "Email already exists"
                    );
                });*/

        employee.setName(request.getName());
        employee.setEmail(request.getEmail());
        employee.setDepartment(request.getDepartment());
        employee.setSalary(request.getSalary());

        Employee updatedEmployee = employeeRepository.save(employee);

        return mapToResponse(updatedEmployee);
    }

    // DELETE
    @Override
    public void deleteEmployee(Long id) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee not found with ID: " + id
                        )
                );

        employeeRepository.delete(employee);
    }
}