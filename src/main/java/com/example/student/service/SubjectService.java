package com.example.student.service;

import com.example.student.exception.ResourceNotFoundException;
import com.example.student.model.Subject;
import com.example.student.model.Teacher;
import com.example.student.reprository.SubjectReprository;
import com.example.student.reprository.TeacherReprository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {
    private final SubjectReprository subjectReprository;

    private final TeacherReprository teacherReprository;

    @Autowired
    public SubjectService(SubjectReprository subjectReprository,  TeacherReprository teacherReprository) {
        this.subjectReprository = subjectReprository;
        this.teacherReprository = teacherReprository;
    }

    public List<Subject> getSubjectService() {
        return subjectReprository.findAll();
    }

    public Subject getSubjectById(Long id)throws ResourceNotFoundException {
        return subjectReprository.findById(id)
                .orElseThrow(()->
                        new ResourceNotFoundException("Student not found for this id :: " + id));
    }

    public Subject createSubjetService(Subject subject) {
        return this.subjectReprository.save(subject);
    }


    public Subject assignTeacherToSubjectService(Long subjectId, Long teacherId) throws ResourceNotFoundException {
        Subject subject=subjectReprository.findById(subjectId).get();
        Teacher teacher=teacherReprository.findById(teacherId).get();
        subject.setTeacher(teacher);
        return subjectReprository.save(subject);
    }

    public void deleteSubjectService(Long id) {
        subjectReprository.deleteById(id);
    }
}
