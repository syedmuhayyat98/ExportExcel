package sam.example.ExportExcel.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sam.example.ExportExcel.domain.Employee;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
    // You can define custom query methods here if needed.

    Page<Employee> findById(String id, Pageable pageable);
    Page<Employee> findByFirstName(String firstName, Pageable pageable);
    Page<Employee> findByLastName(String lastName, Pageable pageable);
    Page<Employee> findByFirstNameAndLastName(String firstName, String lastName, Pageable pageable);

}
