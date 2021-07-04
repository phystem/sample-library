package com.phystem.library.model;

import lombok.*;

import java.util.List;

@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeList {
    private String status;

    private List<EmployeeData> data;

    private String message;
}
