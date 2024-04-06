package sam.example.ExportExcel.controller;

import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sam.example.ExportExcel.domain.Employee;
import sam.example.ExportExcel.exception.InvalidInputException;
import sam.example.ExportExcel.exception.ResourceNotFoundException;
import sam.example.ExportExcel.service.EmployeeService;

import java.util.Optional;


@RestController
@AllArgsConstructor
@RequestMapping("/api/employee/")
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<Page<Employee>> getAllEmployee(
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            Pageable pageable) {

        Page<Employee> employees = employeeService.getAllEmployeeByFilter(id, firstName, lastName, pageable);

        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Optional<Employee>> getEmployeeById(@PathVariable String id) {
        Optional<Employee> employee = employeeService.getEmployeeById(id);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {

        if (StringUtils.isBlank(employee.getId())){
           throw new InvalidInputException("Employee ID from Request Body is null!");
        }
        if (StringUtils.isBlank(employee.getFirstName())){
            throw new InvalidInputException("Employee first name from Request Body is null!");
        }
        if (StringUtils.isBlank(employee.getId())){
            throw new InvalidInputException("Employee last name from Request Body is null!");
        }
        if (StringUtils.isBlank(employee.getEmail())){
            throw new InvalidInputException("Employee email from Request Body is null!");
        }
        if (StringUtils.isBlank(employee.getId())){
            throw new InvalidInputException("Employee joined date from Request Body is null!");
        }
        if (StringUtils.isBlank(employee.getStatus())){
            throw new InvalidInputException("Employee status from Request Body is null!");
        }
        Employee createdEmployee = employeeService.saveEmployee(employee);

        return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteEmployee(@PathVariable String id) {
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping
    public ResponseEntity<Employee> partialUpdateEmployee(@RequestBody Employee employee) {

        Optional<Employee> optionalEmployee = employeeService.findById(employee.getId());

        if (optionalEmployee.isPresent()) {
            Employee updatedEmployee = employeeService.updateEmployee(employee);

            return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException(String.format("Employee with id %s not found", employee.getId()));
        }
    }
    
}

