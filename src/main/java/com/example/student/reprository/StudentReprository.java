package com.example.student.reprository;

import com.example.student.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentReprository extends JpaRepository<Student,Long> {
}
