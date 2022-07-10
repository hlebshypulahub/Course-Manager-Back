package com.hs.coursemanagerback.repository;

import com.hs.coursemanagerback.model.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByForeignId(Long foreignId);

    Optional<Employee> findByIdAndUserId(Long id, Long userId);

    List<Employee> findAllByUserIdAndPharmacy(Long userId, Boolean pharmacy);

    List<Employee> findAllByUserIdAndCategoryIsNullAndEducationIsNull(Long userId);
}
