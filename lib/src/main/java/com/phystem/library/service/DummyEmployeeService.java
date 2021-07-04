package com.phystem.library.service;

import com.phystem.library.model.Employee;
import com.phystem.library.model.EmployeeList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;

public interface DummyEmployeeService {

    @GET("employees")
    Call<EmployeeList> listEmployees();

    @GET("employee/{id}")
    Call<Employee> getEmployee(@Path("id") int user);
}
