package com.company.employeemanagement.service;

import com.company.employeemanagement.dto.EmployeeDTO;
import com.company.employeemanagement.entity.Employee;
import com.company.employeemanagement.entity.User;
import com.company.employeemanagement.exception.ResourceNotFoundException;
import com.company.employeemanagement.repository.EmployeeRepository;
import com.company.employeemanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;

    @Transactional
    public String createEmployee(EmployeeDTO employeeDTO, String currentUsername) {
        if (employeeRepository.findByEmail(employeeDTO.getEmail()) != null) {
            throw new IllegalArgumentException("Employee with this email already exists.");
        }

        Employee newEmployee = new Employee();
        newEmployee.setFirstName(employeeDTO.getFirstName());
        newEmployee.setLastName(employeeDTO.getLastName());
        newEmployee.setEmail(employeeDTO.getEmail());
        newEmployee.setDepartment(employeeDTO.getDepartment());
        newEmployee.setCreatedAt(java.time.LocalDateTime.now());

        User user = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        newEmployee.setCreatedBy(user);

        employeeRepository.save(newEmployee);
        return "Employee created successfully by " + currentUsername;
    }

    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(emp -> new EmployeeDTO(emp.getFirstName(), emp.getLastName(), emp.getEmail(), emp.getDepartment()))
                .collect(Collectors.toList());
    }

    public EmployeeDTO getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
        return new EmployeeDTO(employee.getFirstName(), employee.getLastName(), employee.getEmail(), employee.getDepartment());
    }

    @Transactional
    public String updateEmployee(Long id, EmployeeDTO employeeDTO) {
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));

        existingEmployee.setFirstName(employeeDTO.getFirstName());
        existingEmployee.setLastName(employeeDTO.getLastName());
        existingEmployee.setEmail(employeeDTO.getEmail());
        existingEmployee.setDepartment(employeeDTO.getDepartment());

        employeeRepository.save(existingEmployee);
        return "Employee updated successfully.";
    }

    @Transactional
    public String deleteEmployee(Long id) {
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
        employeeRepository.delete(existingEmployee);
        return "Employee deleted successfully.";
    }
}
