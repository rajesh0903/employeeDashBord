package com.rky.repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.rky.entity.Employee;

public interface EmployeeRepository extends MongoRepository<Employee, Integer> {

}
