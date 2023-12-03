package org.example.service;

import org.example.exception.EmployeeAlreadyAddedException;
import org.example.exception.EmployeeNotFoundException;
import org.example.exception.EmployeeStoragelsFullException;
import org.example.model.Employee;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EmployeeService {

    private static final int MAX_EMPLOYEES = 10;
    private final Map<String, Employee> employees = new HashMap<>();

    public Employee add(String firstName, String lastName) {
        if (employees.size() == MAX_EMPLOYEES) {
            throw new EmployeeStoragelsFullException();
        }
        Employee employee = new Employee(firstName, lastName);
        if (employees.containsKey(employee.getFullName())) {
            throw new EmployeeAlreadyAddedException();
        }
        employees.put(employee.getFullName(), employee);
        return employee;
    }

    public Employee remove(String firstName, String lastName) {
        Employee employee = new Employee(firstName, lastName);
        if (employees.containsKey(employee.getFullName())) {
            return employees.remove((employee.getFullName()));
        }
        throw new EmployeeNotFoundException();
    }

    public Employee find(String firstName, String lastName) {
        Employee employee = new Employee(firstName, lastName);
        if (!employees.containsKey(employee.getFullName())) {
            return employees.get(employee.getFullName());
        }
        throw new EmployeeNotFoundException();
    }

    public Collection<Employee> findAll() {
        return Collections.unmodifiableCollection(employees.values());
    }

}
