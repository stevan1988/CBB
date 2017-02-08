package com.crossballbox.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import com.crossballbox.service.UserService;

public class UserControllerTest {

	private MockMvc mockMvc;
	
	@Autowired
    private UserService UserServiceMock;

//	@Test
	public void downoadImageTest() throws Exception {

		mockMvc.perform(get("/user/post_upload_avatar_file")).andExpect(status().isOk());
//        .andExpect(view().name("todo/list"))
//        .andExpect(forwardedUrl("/WEB-INF/jsp/todo/list.jsp"));
//        .andExpect(model().attribute("todos", hasSize(2)))
//        .andExpect(model().attribute("todos", hasItem(
//                allOf(
//                        hasProperty("id", is(1L)),
//                        hasProperty("description", is("Lorem ipsum")),
//                        hasProperty("title", is("Foo"))
//                )
//        )))
//        .andExpect(model().attribute("todos", hasItem(
//                allOf(
//                        hasProperty("id", is(2L)),
//                        hasProperty("description", is("Lorem ipsum")),
//                        hasProperty("title", is("Bar"))
//                )
//        ));
	}

}
