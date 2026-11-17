package com.example.employemanagement.model;

import java.math.BigDecimal;

public class EmployeeResponse {

    private Long id;
    private String name;
    private String email;
    private String department;
    private BigDecimal salary;


    public EmployeeResponse(Long id, String name, String email,String department, BigDecimal salary ){
        this.id=id;
        this.name=name;
        this.email=email;
        this.department= department;
        this.salary= salary;
    }








}
