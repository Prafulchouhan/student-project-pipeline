package com.example.student.controller;

import com.example.student.model.Student;
import com.example.student.model.Subject;
import com.example.student.model.Teacher;
import com.example.student.service.StudentService;
import com.example.student.service.SubjectService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
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

    @Mock
    private SubjectService subjectService;

    @InjectMocks
    private StudentController studentController;

    Student s1 = Student.builder().id(1L).name("praful")
            .email("praful@gmal.com").build();

    Subject sub= Subject.builder().id(1L).name("maths").build();

    Student s2 = Student.builder().id(2L).name("praful").dob(LocalDate.of(2002,Month.JANUARY,01))
            .email("praful@gmal.com").build();

    Student s3 = Student.builder().id(3L).name("praful").dob(LocalDate.of(2002,Month.JANUARY,01))
            .email("praful@gmal.com").build();


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();
    }

    @Test
    public void getAllSubject() throws Exception {
        List<Student> std = new ArrayList<>(Arrays.asList(s1, s2, s3));
        when(studentService.getStudents()).thenReturn(std);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/students")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
//                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].name", is("praful")));
    }

    @Test
    public void getStudentById() throws Exception {
        when(studentService.getStudentById(s1.getId())).thenReturn(s1);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/students/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
//                .andExpect(status().isOk())
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
    public void deleteStudent() throws Exception {


        this.mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/students/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void postStudent() throws Exception {
        doReturn(s1).when(studentService).saveStudent(any());
        String content = new ObjectMapper().writeValueAsString(s1);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/students")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(content))
                .andDo(print())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is(s1.getName())))
                .andReturn().getResponse().getContentAsString();
    }
    @Test
    public void patch() throws Exception {

        Map<Object,Object> fields = new HashMap<>();

        fields.put("name","abhishek");

        String content = objectWriter.writeValueAsString(fields);

        when(studentService.updateStudentService(1L,fields)).thenReturn(s2);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.patch("/api/v1/students/1")
                .characterEncoding(Charset.defaultCharset())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$",notNullValue()))
                .andExpect(jsonPath("$.name",is(s2.getName())));
    }
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void addSubject() throws Exception {
        when(studentService.addSubjectToStudentService(1L,1L)).thenReturn(s2);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/api/v1/students/1/subject/1")
                .characterEncoding(Charset.defaultCharset())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$",notNullValue()))
                .andExpect(jsonPath("$.name",is(s2.getName())));
    }

}