package com.example.student.reprository;

import com.example.student.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherReprository extends JpaRepository<Teacher,Long> {
}
