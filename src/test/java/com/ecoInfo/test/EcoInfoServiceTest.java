package com.ecoInfo.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.ecoInfo.basic.advice.ExceptionAdvice;
import com.ecoInfo.basic.controller.ProgramController;
import com.ecoInfo.basic.controller.RegionController;
import com.ecoInfo.basic.controller.SearchController;
import com.ecoInfo.basic.model.ProgramDTO;
import com.ecoInfo.basic.model.SearchRegionDTO;
import com.ecoInfo.basic.service.ProgramService;
import com.ecoInfo.basic.util.CustomException;
import com.ecoInfo.basic.util.ParseCSV;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Slf4j
public class EcoInfoServiceTest {

	private MockMvc mockProgramControllerMvc;
	private MockMvc mockRegionControllerMvc;
	private MockMvc mockSearchControllerMvc;

	private ObjectMapper om = new ObjectMapper();

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	ProgramService programService;

	@Autowired
	ProgramController programController;

	@Autowired
	RegionController regionController;

	@Autowired
	SearchController searchController;

	@Autowired
	ExceptionAdvice exHandler;

	@Test
	public void contextLoads() {
	}

	@Before
	public void setUp() throws Exception {
		mockProgramControllerMvc = MockMvcBuilders.standaloneSetup(programController).setControllerAdvice(exHandler)
				.build();
		mockRegionControllerMvc = MockMvcBuilders.standaloneSetup(regionController).setControllerAdvice(exHandler)
				.build();
		mockSearchControllerMvc = MockMvcBuilders.standaloneSetup(searchController).setControllerAdvice(exHandler)
				.build();
	}

	@Test(expected = CustomException.class)
	public void test00_selectProgram_notFound() {
		programService.selectProgram("prg0012");
	}

	@Test
	public void test01_saveProgram() {

		ProgramDTO programDTO = ProgramDTO.builder()
				.prgmDetails("프로그램 상세정보 테스트")
				.prgmIntro("프로그램 정보 테스트")
				.serviceRegion("서울시 강동구")
				.prgmName("프로그램명 테스트")
				.theme("테마 테스트")
				.build();

		programDTO = programService.saveProgram(programDTO);

		ProgramDTO savedProgramDTO = programService.selectProgram(programDTO.getProgram());

		log.debug("savedProgramInfo >> {}", savedProgramDTO);
		Assert.assertEquals(programDTO, savedProgramDTO);

	}

	@Test
	public void test02_csvParseTest() throws IOException {

		InputStream fileStream = getClass().getResourceAsStream("/sample/test.csv");

		ParseCSV.read(HashMap.class, fileStream);

	}

	@Test
	public void test03_csvParseTestToDTO() throws IOException {

		InputStream fileStream = getClass().getResourceAsStream("/sample/test.csv");

		ParseCSV.read(ProgramDTO.ProgramCsvDTO.class, fileStream);
	}

	@Test
	public void test04_csvToBulkInsert() throws IOException {

		InputStream fileStream = getClass().getResourceAsStream("/sample/test.csv");

		programService.importCSV(fileStream);
	}

	@Test
	public void test05_regionApi_01insertCSVProgram() throws Exception {

		InputStream fileStream = getClass().getResourceAsStream("/sample/test.csv");

		MockMultipartFile file = new MockMultipartFile("file", fileStream);
		mockRegionControllerMvc.perform(multipart("/region/importCSV").file(file)).andExpect(status().isCreated());

	}

	@Test
	public void test05_regionApi_02getList() throws Exception {

		mockRegionControllerMvc.perform(get("/region")).andExpect(status().isOk());
	}

	@Test
	public void test05_regionApi_03getProgram() throws Exception {

		mockRegionControllerMvc.perform(get("/region/{regionId}", "reg0001")).andExpect(status().isOk());
	}

	@Test
	public void test06_programApi_01insertProgram() throws Exception {

		ProgramDTO dto = ProgramDTO.builder()
				.prgmName("테스트")
				.theme("소풍")
				.serviceRegion("강원도 원주")
				.prgmIntro("미로시장")
				.prgmDetails("골목식당 미로시장 맛집탐방")
				.build();

		mockProgramControllerMvc.perform(
				post("/program").content(om.writeValueAsString(dto)).contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk());
	}

	@Test
	public void test06_programApi_02updateProgram() throws Exception {

		test06_programApi_01insertProgram();

		ProgramDTO dto = ProgramDTO.builder()
				.prgmName("테스트")
				.theme("소풍")
				.serviceRegion("강원도 원주")
				.prgmIntro("미로시장")
				.prgmDetails("골목식당 미로시장 맛집탐방")
				.build();

		mockProgramControllerMvc.perform(patch("/program/{programId}", "prg0001").content(om.writeValueAsString(dto))
				.contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
	}

	@Test
	public void test07_searchApi_01searchRegion() throws Exception {

		ProgramDTO dto = ProgramDTO.builder()
				.prgmName("테스트")
				.theme("소풍")
				.serviceRegion("강원도 원주")
				.prgmIntro("미로시장")
				.prgmDetails("골목식당 미로시장 맛집탐방")
				.build();

		dto = programService.saveProgram(dto);

		mockSearchControllerMvc.perform(
				post("/search/region").content(om.writeValueAsString(SearchRegionDTO.builder().region("강원도").build()))
						.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk());
	}
}
