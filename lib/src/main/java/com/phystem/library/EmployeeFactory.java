package com.phystem.library;

import com.phystem.library.exception.EmployeeException;
import com.phystem.library.model.Employee;
import com.phystem.library.model.EmployeeData;
import com.phystem.library.service.DummyEmployeeService;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.util.List;

public class EmployeeFactory {

    public static String DUMMY_EMPLOYEE_URL = "https://dummy.restapiexample.com/api/v1/";

    private final DummyEmployeeService dummyEmployeeService;

    public EmployeeFactory() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(DUMMY_EMPLOYEE_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        dummyEmployeeService = retrofit.create(DummyEmployeeService.class);
    }

    /**
     * @return all the employees by the dummy api
     */
    public List<EmployeeData> getAllEmployees() {
        try {
            var response = dummyEmployeeService.listEmployees().execute();
            if (response.isSuccessful()) {
                return response.body().getData();
            } else {
                throw new EmployeeException("Error fetching data from dummy service - " + response.toString());
            }
        } catch (IOException e) {
            throw new EmployeeException("Could not fetch Employees", e);
        }
    }

    /**
     * @param id Id of the employee
     * @return employee belonging to the id
     */
    public EmployeeData getEmployeeById(int id) {
        try {
            if (id <= 0) {
                throw new EmployeeException("Invalid Employee Id " + id);
            }
            var response = dummyEmployeeService.getEmployee(id).execute();
            if (response.isSuccessful()) {
                return response.body().getData();
            } else {
                throw new EmployeeException("Error fetching data from dummy service - " + response.toString());
            }
        } catch (IOException e) {
            throw new EmployeeException("Could not fetch employees with id " + id, e);
        }
    }

}
