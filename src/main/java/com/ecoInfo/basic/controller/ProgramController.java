package com.ecoInfo.basic.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecoInfo.basic.model.ProgramDTO;
import com.ecoInfo.basic.service.ProgramService;


@RestController
@RequestMapping("/program")
public class ProgramController {

	@Autowired
	private ProgramService programService;

	/**
	  * @Method Name : insertProgram
	  * @작성일 : 2019. 7. 20.
	  * @작성자 : yblee
	  * @변경이력 : 
	  * @Method 설명 : 생태 관광정보 데이터 추가 API
	  * @param programDTO
	  * @return
	  */
	@PostMapping
	public ProgramDTO insertProgram(@RequestBody @Valid ProgramDTO programDTO) {
		return programService.saveProgram(programDTO);
	}

	/**
	  * @Method Name : updateProgram
	  * @작성일 : 2019. 7. 20.
	  * @작성자 : yblee
	  * @변경이력 : 
	  * @Method 설명 : 생태 관광정보 데이터 수정 API
	  * @param programId
	  * @param programDTO
	  * @return
	  */
	@PatchMapping("/{programId}")
	public ProgramDTO updateProgram(@PathVariable("programId") String programId,
			@RequestBody @Valid ProgramDTO programDTO) {
		programDTO.setProgram(programId);
		return programService.saveProgram(programDTO);
	}
}
