package com.example.student.controller;

import com.example.student.exception.ResourceNotFoundException;
import com.example.student.model.Subject;
import com.example.student.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/subject")
public class SubjectController {
    private final SubjectService subjectService;

    @Autowired
    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping
    public List<Subject> getSubject(){
        return subjectService.getSubjectService();
    }

    @PostMapping
    public Subject createSubjet(@RequestBody Subject subject){

        return subjectService.createSubjetService(subject);
    }

    @PutMapping("/{subjectId}/teacher/{teacherId}")
    public Subject assignTeacherToSubject(
            @PathVariable Long subjectId,
            @PathVariable Long teacherId
    ) throws ResourceNotFoundException {
        return subjectService.assignTeacherToSubjectService(subjectId,teacherId);
    }


    @DeleteMapping("/{id}")
    public List<Subject> deleteSubject(
            @PathVariable Long id
    ) throws ResourceNotFoundException {
        return subjectService.deleteSubjectService(id);
    }

}

