package com.phystem.library;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.phystem.library.exception.EmployeeException;
import com.phystem.library.model.EmployeeData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EmployeeFactoryTest {

    private EmployeeFactory employeeFactory;

    private WireMockServer wireMockServer;

    @BeforeEach
    void setUp() {
        wireMockServer = new WireMockServer(options().dynamicPort());
        wireMockServer.start();
        EmployeeFactory.DUMMY_EMPLOYEE_URL = wireMockServer.baseUrl();
        employeeFactory = new EmployeeFactory();
    }

    @AfterEach
    void afterMethod() {
        wireMockServer.stop();
    }

    @Test
    void getAllEmployeesTest() {
        wireMockServer.stubFor(get(urlEqualTo("/employees"))
                .willReturn(aResponse().withBodyFile("employees.json")));

        List<EmployeeData> employeeList = employeeFactory.getAllEmployees();
        assertEquals(24, employeeList.size());
    }

    @Test
    void getEmployeeByIdTest() {
        wireMockServer.stubFor(get(urlEqualTo("/employee/1"))
                .willReturn(aResponse().withBodyFile("employee.json")));

        EmployeeData employee = employeeFactory.getEmployeeById(1);
        assertNotNull(employee);
        assertEquals("Tiger Nixon", employee.getEmployeeName());
    }

    @Test
    void serverUnavailableTest() {
        wireMockServer.stubFor(get(urlEqualTo("/employee/1"))
                .willReturn(aResponse().withStatus(500).withStatusMessage("serverUnavailable")));
        Throwable invalidIdException = assertThrows(EmployeeException.class, () -> {
            EmployeeData employee = employeeFactory.getEmployeeById(1);
        });
        assertTrue(invalidIdException.getMessage()
                        .startsWith("Error fetching data from dummy service"),
                invalidIdException.getMessage());
    }

    @Test
    void getEmployeeByInvalidIdTest() {
        Throwable invalidIdException = assertThrows(EmployeeException.class, () -> {
            employeeFactory.getEmployeeById(-1);
        });
        assertEquals("Invalid Employee Id -1", invalidIdException.getMessage());
    }
}