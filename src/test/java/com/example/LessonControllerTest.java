package com.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.Collections;
import java.util.Random;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.verify;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by localadmin on 3/14/17.
 */

@RunWith(SpringRunner.class)
@WebMvcTest(LessonController.class)
public class LessonControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    LessonRepository repository;

    @Test
    public void testCreate() throws Exception {
        MockHttpServletRequestBuilder request = post("/lessons")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\": \"Random Lesson\"}");

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", equalTo("Random Lesson")));

        verify(this.repository).save(any(Lesson.class));
    }

    @Test
    public void testList()throws Exception{
        Long id = new Random().nextLong();
        Lesson ourLesson = new Lesson();
        ourLesson.setTitle("Random Lesson");
        ourLesson.setId(id);

        when(this.repository.findAll())
                .thenReturn(Collections.singletonList(ourLesson));

        this.mvc.perform(get("/lessons"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title", equalTo("Random Lesson")))
                .andExpect(jsonPath("$[0].id", equalTo(id)));
    }


}
