package com.example.student.service;

import com.example.student.model.Student;
import com.example.student.model.Teacher;
import com.example.student.reprository.StudentReprository;
import com.example.student.reprository.SubjectReprository;
import com.example.student.reprository.TeacherReprository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {
    private final SubjectReprository subjectReprository;

    private final StudentReprository studentReprository;

    private final TeacherReprository teacherReprository;

    @Autowired
    public TeacherService(SubjectReprository subjectReprository, StudentReprository studentReprository, TeacherReprository teacherReprository) {
        this.subjectReprository = subjectReprository;
        this.studentReprository = studentReprository;
        this.teacherReprository = teacherReprository;
    }

    public List<Teacher> getTeachersService() {
        return this.teacherReprository.findAll();
    }

    public Teacher createTeacherService(Teacher teacher) {
        return this.teacherReprository.save(teacher);
    }
    public List<Teacher> deleteTeacherService(Long id) {
        Teacher teacher=teacherReprository.findById(id).get();
        teacherReprository.delete(teacher);
        return teacherReprository.findAll();
    }
}
