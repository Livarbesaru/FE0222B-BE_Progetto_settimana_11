package com.dblibrary.test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;

import com.dblibrary.model.library.Author;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@WithMockUser(username="user", password="password", authorities = {"ROLE_USER"})
public class AuthorTest {

	@Autowired 
	MockMvc mockMvc;
	
//	@Test
//	void getAuthor() throws Exception {
//		this.mockMvc.perform(get("http://localhost:8080/author/get/4")).andExpect(status().isOk());
//	}
//
//	@Test
//	void getAllAuthors() throws Exception {
//		this.mockMvc.perform(get("http://localhost:8080/author/get/all")).andExpect(status().isOk());
//	}
//
//	@Test
//	@WithMockUser(username = "user", password = "password", authorities = { "ROLE_ADMIN" })
//	public void addAuthor() throws Exception {
//
//		Author author = new Author();
//		String name = "Daniele";
//		String surname = "Verdoni";
//		author.setName(name);
//		author.setSurname(surname);
//
//		ObjectMapper objectMapper = new ObjectMapper();
//		String json = objectMapper.writeValueAsString(author);
//
//		MvcResult result = mockMvc
//				.perform(post("http://localhost:8080/author/add").contentType(MediaType.APPLICATION_JSON).content(json))
//				.andExpect(status().isOk())
//				.andExpect((ResultMatcher) content().json("{'name':'" + name.toLowerCase() + "'}"))
//				.andExpect((ResultMatcher) content().json("{'surname':'" + surname.toLowerCase() + "'}")).andReturn();
//	}
//
//	@Test
//	@WithMockUser(username = "user", password = "password", authorities = { "ROLE_ADMIN" })
//	void testDeleteAuthor() throws Exception {
//		this.mockMvc.perform(delete("http://localhost:8080/author/delete/4")).andExpect(status().isOk())
//				.andExpect((ResultMatcher) content().string(containsString("eliminato")));
//	}
}
