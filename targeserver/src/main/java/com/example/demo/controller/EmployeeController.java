package com.example.demo.controller;

import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

//@RestController
//@RequestMapping("/employees")
public class EmployeeController {

	@Autowired
    private EmployeeRepository employeeRepository;



	@GetMapping("/{id}")
	private Mono<Employee> getEmployeeById(@PathVariable String id) {
		return employeeRepository.findEmployeeById(id);
	}
}