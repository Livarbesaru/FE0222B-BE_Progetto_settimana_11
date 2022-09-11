package com.dblibrary.test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@WithMockUser(username="user", password="password", authorities = {"ROLE_USER"})
class BookTests {

	@LocalServerPort
	private int port;

	@Autowired MockMvc mockMvc;
//	
//	@Test
//	void getBookById() throws Exception {
//		this.mockMvc.perform(get("http://localhost:8080/book/get/26")).andExpect(status().isOk());
//	}
//
//	@Test
//	void findByCategory() throws Exception {
//		this.mockMvc.perform(get("http://localhost:8080/book/get/categorybooks/24")).andExpect(status().isOk());
//	}
//
//	@Test
//	void findByAuthor() throws Exception {
//		this.mockMvc.perform(get("http://localhost:8080/book/get/authorbooks/4")).andExpect(status().isOk());
//	}
//
//	@Test
//	@WithMockUser(username = "user", password = "password", authorities = { "ROLE_ADMIN" })
//	void deleteBook() throws Exception {
//		this.mockMvc.perform(delete("http://localhost:8080/book/delete/1")).andExpect(status().isOk())
//				.andExpect((ResultMatcher) content().string(containsString("e' stato cancellato")));
//	}
}