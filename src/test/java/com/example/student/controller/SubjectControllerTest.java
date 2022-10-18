package com.example.student.controller;

import com.example.student.model.Subject;
import com.example.student.service.SubjectService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SubjectControllerTest {

    private MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    @Mock
    private SubjectService subjectService;

    @InjectMocks
    private SubjectController subjectController;

    Subject s1 = Subject.builder().id(1L).name("Maths").build();
    Subject s2 = Subject.builder().id(2L).name("Maths").build();
    Subject s3 = Subject.builder().id(3L).name("Maths").build();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(subjectController).build();
    }

    @Test
    public void getAllSubject() throws Exception {
        List<Subject> std = new ArrayList<>(Arrays.asList(s1, s2, s3));
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
    public void deleteSubject() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/subject/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void saveSubject() throws Exception {
        doReturn(s1).when(subjectService).createSubjetService(any());
        String content = new ObjectMapper().writeValueAsString(s1);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/subject")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(content))
                .andDo(print())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is(s1.getName())))
                .andReturn().getResponse().getContentAsString();

    }

    @Test
    public void assignTeacher() throws Exception {
        when(subjectService.assignTeacherToSubjectService(1L,1L)).thenReturn(s2);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/api/v1/subject/1/teacher/1")
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