package com.example.student.controller;

import com.example.student.model.Student;
import com.example.student.model.Teacher;
import com.example.student.service.StudentService;
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
public class StudentControllerTest {

    private MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    Student s1 = new Student(1L, "praful", "praful@gmail.com", null);

    Student s2 = new Student(2L, "praful", "praful@gmail.com", LocalDate.of(2002, Month.JANUARY, 01));

    Student s3 = new Student(3L, "praful", "praful@gmail.com", LocalDate.of(2002, Month.JANUARY, 01));


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();
    }

    @Test
    public void getAllStudents() throws Exception {
        List<Student> std = new ArrayList<>(Arrays.asList(s1, s2, s3));
        Mockito.when(studentService.getStudents()).thenReturn(std);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/students")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[2].name", is("praful")));
    }

    @Test
    public void getStudentById() throws Exception {
        Mockito.when(studentService.getStudentById(s1.getId())).thenReturn(s1);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/students/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is("praful")));
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
    @Test
    public void deleteTeacher() throws Exception {

//        List<Teacher> teachers = new ArrayList<>(Arrays.asList(t1,t2,t3));
//        Mockito.when(teacherService.deleteTeacherService(t1.getId())).thenReturn(teachers);
//
//        mockMvc.perform(MockMvcRequestBuilders
//                        .delete("api/v1/teacher/1")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$",hasSize(2)));


        this.mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/students/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void postStuent() throws Exception {

//        Mockito.when(teacherService.createTeacherService(t1)).thenReturn(t1);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/teacher")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(new Teacher(1L, "abhi")))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void saveStudent() throws Exception {
        Mockito.when(studentService.saveStudent(s1)).thenReturn(s1);
        String content = objectWriter.writeValueAsString(s1);
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.post("/api/v1/student")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(s1));
        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", notNullValue()));

    }
}