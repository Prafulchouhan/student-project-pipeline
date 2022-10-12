package com.example.student.controller;


import com.example.student.model.Teacher;
import com.example.student.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/teacher")
public class TeacherController {
    private final TeacherService teacherService;

    @Autowired
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping
    public List<Teacher> getTeachers(){
        return teacherService.getTeachersService();
    }

    @PostMapping
    public Teacher createTeacher(@RequestBody Teacher teacher) {
        return teacherService.createTeacherService(teacher);
    }

    @DeleteMapping("/{id}")
    public List<Teacher> deleteTeacher(
            @PathVariable Long id
    ){
        return teacherService.deleteTeacherService(id);
    }

}
