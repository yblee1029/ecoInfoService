package com.ecoInfo.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.ecoInfo.basic.advice.ExceptionAdvice;
import com.ecoInfo.selection.controller.JwtController;
import com.ecoInfo.selection.model.JwtDTO;
import com.ecoInfo.selection.model.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class JwtServiceTest {

	private MockMvc mockJwtControllerMvc;

	private ObjectMapper om = new ObjectMapper();
	
	private UserDTO userDTO;
	
	private JwtDTO jwtDTO;

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	JwtController jwtController;

	@Autowired
	ExceptionAdvice exHandler;

	@Test
	public void contextLoads() {
	}

	@Before
	public void setUp() throws Exception {
		mockJwtControllerMvc = MockMvcBuilders.standaloneSetup(jwtController).setControllerAdvice(exHandler)
				.build();
		
		userDTO = UserDTO.builder()
				.userid("test")
				.password("1234")				
				.build();
		
		MvcResult result = mockJwtControllerMvc.perform(
				post("/jwt/signUp").content(om.writeValueAsString(userDTO)).contentType(MediaType.APPLICATION_JSON_UTF8)).andReturn();
		String content = result.getResponse().getContentAsString();
		 
		ObjectMapper mapper = new ObjectMapper();
		jwtDTO = mapper.readValue(content, JwtDTO.class);
		
	}
	
	@Test
	public void test01_jwtApi_01signUp() throws Exception {

		UserDTO dto = UserDTO.builder()
				.userid("test1")
				.password("1234")				
				.build();

		mockJwtControllerMvc.perform(
				post("/jwt/signUp").content(om.writeValueAsString(dto)).contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk());
	}
	
	@Test
	public void test02_jwtApi_01signIn() throws Exception {

		UserDTO dto = UserDTO.builder()
				.userid("test")
				.password("1234")				
				.build();

		mockJwtControllerMvc.perform(
				post("/jwt/signIn").content(om.writeValueAsString(dto)).contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk());
	}

	@Test
	public void test03_jwtApi_01refresh() throws Exception {

		mockJwtControllerMvc.perform(
				post("/jwt/refresh").content(om.writeValueAsString(jwtDTO)).header("Authorization", "Bearer Token")
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk());
	}
}
