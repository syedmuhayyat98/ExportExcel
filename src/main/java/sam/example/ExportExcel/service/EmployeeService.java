package sam.example.ExportExcel.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sam.example.ExportExcel.domain.Employee;
import sam.example.ExportExcel.exception.ResourceAlreadyExistedException;
import sam.example.ExportExcel.exception.ResourceNotFoundException;
import sam.example.ExportExcel.repository.EmployeeRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Transactional
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public Page<Employee> getAllEmployeeByFilter(String id,
                                           String firstName,
                                           String lastName,
                                           Pageable pageable) {
        Page<Employee> employee = employeeRepository.findAll(pageable);

        List<Employee> filteredEmployee = employee.getContent();

        //Filter by id
        if (id != null){
            filteredEmployee = filteredEmployee.stream()
                    .filter(employees -> employees.getId().toLowerCase().contains(id.toLowerCase()))
                    .collect(Collectors.toList());
        }

        //Filter by firstName
        if (firstName != null){
            filteredEmployee = filteredEmployee.stream()
                    .filter(employees -> employees.getFirstName().toLowerCase().contains(firstName.toLowerCase()))
                    .collect(Collectors.toList());
        }

        //Filter by lastName
        if (lastName != null){
            filteredEmployee = filteredEmployee.stream()
                    .filter(employees -> employees.getLastName().toLowerCase().contains(lastName.toLowerCase()))
                    .collect(Collectors.toList());
        }


        return new PageImpl<>(filteredEmployee, pageable, filteredEmployee.size());
    }

    public Optional<Employee> findById(String id) {
        return employeeRepository.findById(id);
    }

    public Optional<Employee> getEmployeeById(String id) {
        Optional<Employee> employee = employeeRepository.findById(id);

        if (employee.isPresent()) {
            return employee;
        } else {
            throw new ResourceNotFoundException(String.format("Employee with id %s is not found", id));
        }
    }

    public Employee saveEmployee(Employee employee) {
        // Add any validation or business logic before saving the employee.
        Optional<Employee> optionalEmployee = employeeRepository.findById(employee.getId());
        if (optionalEmployee.isEmpty()){
            return employeeRepository.save(employee);
        }
        else throw  new ResourceAlreadyExistedException(String.format("Employee with id %s are already exist", optionalEmployee.get().getId()));
    }

    public void deleteEmployee(String id) {
        if (!employeeRepository.existsById(id)){
            throw new ResourceAlreadyExistedException(String.format("Employee with id %s not found", id));
        }
        employeeRepository.deleteById(id);
    }

    public Employee updateEmployee(Employee updatedEmployee) {

        Employee existingEmployee = employeeRepository.findById(updatedEmployee.getId())
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with ID: " + updatedEmployee.getId()));
        // Update employee fields from the DTO
        if (updatedEmployee.getFirstName() != null) {
            existingEmployee.setFirstName(updatedEmployee.getFirstName());
        }
        if (updatedEmployee.getLastName() != null) {
            existingEmployee.setLastName(updatedEmployee.getLastName());
        }
        if (updatedEmployee.getEmail() != null) {
            existingEmployee.setEmail(updatedEmployee.getEmail());
        }
        if (updatedEmployee.getJoinedDate() != null) {
            existingEmployee.setJoinedDate(updatedEmployee.getJoinedDate());
        }
        if (updatedEmployee.getStatus() != null) {
            existingEmployee.setStatus(updatedEmployee.getStatus());
        }
        if (updatedEmployee.getDesignation() != null) {
            existingEmployee.setDesignation(updatedEmployee.getDesignation());
        }
            // Update other fields as needed

            // Save the updated employee back to the database
            return employeeRepository.save(existingEmployee);
    }


    public boolean existsById(String id){
        return employeeRepository.existsById(id);
    }
}

