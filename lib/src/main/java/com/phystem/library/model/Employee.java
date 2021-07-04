package com.phystem.library.model;

import lombok.*;

@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    private String status;
    private EmployeeData data;
    private String message;
}
