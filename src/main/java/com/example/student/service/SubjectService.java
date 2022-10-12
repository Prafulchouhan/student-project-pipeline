package com.example.student.service;

import com.example.student.model.Subject;
import com.example.student.model.Teacher;
import com.example.student.reprository.StudentReprository;
import com.example.student.reprository.SubjectReprository;
import com.example.student.reprository.TeacherReprository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {
    private final SubjectReprository subjectReprository;

    private final StudentReprository studentReprository;

    private final TeacherReprository teacherReprository;

    @Autowired
    public SubjectService(SubjectReprository subjectReprository, StudentReprository studentReprository, TeacherReprository teacherReprository) {
        this.subjectReprository = subjectReprository;
        this.studentReprository = studentReprository;
        this.teacherReprository = teacherReprository;
    }

    public List<Subject> getSubjectService() {
        return subjectReprository.findAll();
    }

    public Subject createSubjetService(Subject subject) {
        return this.subjectReprository.save(subject);
    }

//    public Student getStudentById(Long id)throws ResourceNotFoundException
//    {
//
//    }
    public Subject assignTeacherToSubjectService(Long subjectId, Long teacherId) {
        Subject subject=subjectReprository.findById(subjectId).get();
        Teacher teacher=teacherReprository.findById(teacherId).get();
        subject.assignTeacher(teacher);
        return subjectReprository.save(subject);
    }

    public List<Subject> deleteSubjectService(Long id) {
        Subject subject=subjectReprository.findById(id).get();
        subjectReprository.delete(subject);
        return subjectReprository.findAll();
    }
}
