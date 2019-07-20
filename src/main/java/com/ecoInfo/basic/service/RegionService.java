package com.ecoInfo.basic.service;

import java.util.List;

import com.ecoInfo.basic.domain.Region;
import com.ecoInfo.basic.model.ProgramDTO;
import com.ecoInfo.basic.model.RegionDTO;

public interface RegionService {

	List<Region> createOrGetRegionListByServiceRegionText(String serviceRegion);

	Region createOrGetRegionByRegionName(String regionName, String rootName);

	List<String> getRegionNameListByServiceRegion(String serviceRegion);

	List<RegionDTO> getRegionList();

	List<ProgramDTO> getRegionProgramList(String regionId);
}
