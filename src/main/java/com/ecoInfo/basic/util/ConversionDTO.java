package com.ecoInfo.basic.util;

import java.util.function.Function;

import com.ecoInfo.basic.domain.Program;
import com.ecoInfo.basic.domain.Region;
import com.ecoInfo.basic.model.ProgramDTO;
import com.ecoInfo.basic.model.RegionDTO;

public class ConversionDTO {

	public final static Function<Program, ProgramDTO> PROGRAM_TO_DTO = (program) -> {
		return ProgramDTO.builder()
				.program(program.getId())
				.prgmName(program.getPrgmName())
				.serviceRegion(program.getServiceRegion())
				.prgmIntro(program.getPrgmIntro())
				.prgmDetails(program.getPrgmDetails())
				.theme(program.getTheme())
				.build();
	};

	public final static Function<Region, RegionDTO> REGION_TO_DTO = (region) -> {
		return RegionDTO.builder()
				.region(region.getId())
				.regionName(region.getFullRegionName())
				.build();
	};

}
