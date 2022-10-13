package com.example.homework_stream.service;

import com.example.homework_stream.exeption.EmployeeAlreadyAddedException;
import com.example.homework_stream.exeption.EmployeeNotFoundException;
import com.example.homework_stream.exeption.IncorrectInputException;
import com.example.homework_stream.model.Employee;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final List<Employee> employees;

    public EmployeeServiceImpl(List<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public Employee add(String firstName, String lastName, int salary, int department) {
        checkUserName(firstName, lastName);
        Employee employee = new Employee(firstName, lastName, salary, department);
        if (employees.contains(employee)) {
            throw new EmployeeAlreadyAddedException();
        }
        employees.add(employee);
        return employee;
    }

    @Override
    public Employee remove(String firstName, String lastName) {
        checkUserName(firstName, lastName);
        Employee employee = find(firstName, lastName);
        employees.remove(employee);

        throw new EmployeeNotFoundException();
    }

    @Override
    public Employee find(String firstName, String lastName) {
        checkUserName(firstName, lastName);
        Optional<Employee> employee = employees.stream()
                .filter(e -> e.getFirstName().equals(firstName) && e.getLastName().equals(lastName))
                .findAny();

        return employee.orElseThrow(EmployeeNotFoundException::new);
    }

    @Override
    public Employee getMaxSalary(int department) {
        return employees.stream()
                .filter(e -> e.getDepartment() == department)
                .max(Comparator.comparingInt(employee -> employee.getSalary()))
                .orElseThrow(EmployeeNotFoundException::new);

    }

    @Override
    public Employee getMinSalary(int department) {
        return employees.stream()
                .filter(e -> e.getDepartment() == department)
                .min(Comparator.comparingInt(Employee::getSalary))
                .orElseThrow(EmployeeNotFoundException::new);
    }

    @Override
    public List<Employee> getAllEmployeeInDepartment(int department) {
        return employees.stream()
                .filter(e -> e.getDepartment() == department)
                .collect(Collectors.toList());

    }

    @Override
    public List<Employee> getAllEmployeeMultipleDepartment() {
        return employees.stream()
                .sorted(Comparator.comparingInt(Employee::getDepartment))
                .collect(Collectors.toList());
    }


    @Override
    public Collection<Employee> findAll() {
        return Collections.unmodifiableCollection(employees);
    }

    private void checkUserName(String firstName, String lastName) {
        if (!StringUtils.isAlpha(firstName) && StringUtils.isAlpha(lastName)) {
            throw new IncorrectInputException();
        }
    }


    public List<Employee> getEmployees() {
        employees.add(new Employee("Иван", "Гордуни", 30000, 2));
        employees.add(new Employee("Вася", "Обломов", 25000, 1));
        employees.add(new Employee("Криан", "Кошкин", 24000, 3));
        employees.add(new Employee("Рома", "Петросян", 20000, 2));
        employees.add(new Employee("Федя", "Кляйн", 15000, 3));
        employees.add(new Employee("Ирина", "Одинокая", 40000, 1));
        return employees;
    }
}