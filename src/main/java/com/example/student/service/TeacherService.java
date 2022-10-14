package com.example.student.service;

import com.example.student.exception.ResourceNotFoundException;
import com.example.student.model.Teacher;
import com.example.student.reprository.TeacherReprository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {

    private final TeacherReprository teacherReprository;

    @Autowired
    public TeacherService(TeacherReprository teacherReprository) {
        this.teacherReprository = teacherReprository;
    }

    public List<Teacher> getTeachersService() {
        return this.teacherReprository.findAll();
    }

    public Teacher getTeacherById(Long id)throws ResourceNotFoundException {
        return teacherReprository.findById(id)
                .orElseThrow(()->
                        new ResourceNotFoundException("Student not found for this id :: " + id));
    }
    public Teacher createTeacherService(Teacher teacher) {
        return this.teacherReprository.save(teacher);
    }

    public void deleteTeacherService(Long id) {
        teacherReprository.deleteById(id);
    }
}
