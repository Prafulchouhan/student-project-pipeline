package com.example.student.reprository;

import com.example.student.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectReprository extends JpaRepository<Subject,Long> {
}
