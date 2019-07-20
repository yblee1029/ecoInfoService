package com.ecoInfo.basic.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ecoInfo.basic.model.ProgramDTO;
import com.ecoInfo.basic.model.RegionDTO;
import com.ecoInfo.basic.service.ProgramService;
import com.ecoInfo.basic.service.RegionService;

@RestController
@RequestMapping("/region")
public class RegionController {

	@Autowired
	private ProgramService programService;
	
	@Autowired
	private RegionService regionService;

	/**
	  * @Method Name : insertCSV
	  * @작성일 : 2019. 7. 20.
	  * @작성자 : yblee
	  * @변경이력 : 
	  * @Method 설명 : 데이터 파일에서 각 레코드를 데이터베이스에 저장하는 API
	  * @param file
	  * @throws IOException
	  */
	@PostMapping("/importCSV")
	@ResponseStatus(HttpStatus.CREATED)
	public void insertCSV(@RequestParam("file") @NotNull MultipartFile file) throws IOException {
		programService.importCSV(file.getInputStream());
	}

	/**
	  * @Method Name : getList
	  * @작성일 : 2019. 7. 20.
	  * @작성자 : yblee
	  * @변경이력 : 
	  * @Method 설명 : 생태 관광정보 지역 목록 조회 API
	  * @return
	  */
	@GetMapping
	public List<RegionDTO> getList() {
		return regionService.getRegionList();
	}

	/**
	  * @Method Name : getProgram
	  * @작성일 : 2019. 7. 20.
	  * @작성자 : yblee
	  * @변경이력 : 
	  * @Method 설명 : 서비스 지역 코드를 기준으로 지역 조회 API
	  * @param regionId
	  * @return
	  */
	@GetMapping("/{regionId}")
	public List<ProgramDTO> getProgram(@PathVariable("regionId") String regionId) {
		return regionService.getRegionProgramList(regionId);
	}
}
