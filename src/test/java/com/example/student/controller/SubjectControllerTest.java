package com.example.student.controller;

import com.example.student.model.Student;
import com.example.student.model.Subject;
import com.example.student.service.StudentService;
import com.example.student.service.SubjectService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@RunWith(MockitoJUnitRunner.class)
public class SubjectControllerTest {

    private MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    @Mock
    private SubjectService subjectService;

    @InjectMocks
    private SubjectController subjectController;

    Subject s1=new Subject(1L,"Maths");
    Subject s2=new Subject(2L,"Maths");
    Subject s3=new Subject(3L,"Maths");

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(subjectController).build();
    }

    @Test
    public void getAllSubject() throws Exception {
        List<Subject> std = new ArrayList<>(Arrays.asList(s1,s2,s3));
        Mockito.when(subjectService.getSubjectService()).thenReturn(std);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/subject")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[2].name", is("Maths")));
    }

    @Test
    public void getSubjectById() throws Exception {
        Mockito.when(subjectService.getSubjectById(s1.getId())).thenReturn(s1);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/subject/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is("Maths")));
    }
    @Test
    public void createSubject() throws Exception {
        Mockito.when(subjectService.createSubjetService(s1)).thenReturn(s1);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/subject")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",notNullValue()))
                .andExpect(jsonPath("$.name", is("Maths"))
        );
    }

//    @Test
//    public void saveStudent() throws Exception {
//        Student record = new Student(3L,"praful","praful@gmail.com", LocalDate.of(2002, Month.JANUARY,01));
//        Mockito.when(studentService.saveStudent(record)).thenReturn(record);
//        String content = objectWriter.writeValueAsString(record);
//        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.post("/api/v1/students")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .content(content);
//        mockMvc.perform(mockHttpServletRequestBuilder)
//                .andDo(print())
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$", notNullValue()))
//                .andExpect(jsonPath("$.name", is("praful")));
//
//    }
}