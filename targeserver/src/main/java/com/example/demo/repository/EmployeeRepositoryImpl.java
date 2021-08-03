package com.example.demo.repository;

import com.example.demo.model.Employee;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class EmployeeRepositoryImpl implements EmployeeRepository{
    @Override
    public Mono<Employee> findEmployeeById(String id) {
        System.out.println("=========="+id);
        return Mono.just(Employee.builder().id(id).name("test").build());
//        return Mono.just( new Employee(id, "test"));
    }
}
