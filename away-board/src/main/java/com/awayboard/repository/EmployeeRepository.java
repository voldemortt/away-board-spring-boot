package com.awayboard.repository;

import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.awayboard.model.Employee;

/**
 * The Interface EmployeeRepository.
 */
@Transactional
public interface EmployeeRepository extends JpaRepository<Employee,String> {	

}