package com.ecoInfo.basic.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecoInfo.basic.domain.Program;
import com.ecoInfo.basic.domain.Region;
import com.ecoInfo.basic.model.ProgramDTO;
import com.ecoInfo.basic.repository.ProgramRepository;
import com.ecoInfo.basic.service.ProgramService;
import com.ecoInfo.basic.service.RegionService;
import com.ecoInfo.basic.util.ConversionDTO;
import com.ecoInfo.basic.util.CustomException;
import com.ecoInfo.basic.util.ParseCSV;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProgramServiceImpl implements ProgramService {

	@Autowired
	private RegionService regionService;

	@Autowired
	private ProgramRepository programRepository;

	@Override
	@Transactional
	public void importCSV(InputStream is) {

		List<ProgramDTO.ProgramCsvDTO> csvDtoList = null;
		try {

			csvDtoList = ParseCSV.read(ProgramDTO.ProgramCsvDTO.class, is);

		} catch (IOException e) {
			log.error(e.getMessage());
			new RuntimeException(e.getCause());
		}

		Optional.ofNullable(csvDtoList).ifPresent(dtoList -> {
			dtoList.stream().forEach(dto -> {
				saveProgram(dto);
			});
		});
	}

	@Override
	@Transactional
	public ProgramDTO saveProgram(ProgramDTO programDTO) {

		Program program = Program.builder()
				.id(programDTO.getProgram())
				.prgmName(programDTO.getPrgmName())
				.serviceRegion(programDTO.getServiceRegion())
				.prgmIntro(programDTO.getPrgmIntro())
				.prgmDetails(programDTO.getPrgmDetails())
				.theme(programDTO.getTheme())
				.regionMapping(new HashSet<Region>(regionService.createOrGetRegionListByServiceRegionText(programDTO.getServiceRegion())))
				.build();

		program = programRepository.save(program);

		programDTO.setProgram(program.getId());
		return programDTO;
	}

	@Override
	public ProgramDTO selectProgram(String programId) {

		return programRepository.findById(programId).map(ConversionDTO.PROGRAM_TO_DTO)
				.orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND));
	}

}
