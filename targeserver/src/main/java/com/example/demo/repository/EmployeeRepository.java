package com.example.demo.repository;

import com.example.demo.model.Employee;
import reactor.core.publisher.Mono;

public interface EmployeeRepository {
    Mono<Employee> findEmployeeById(String id);
}
