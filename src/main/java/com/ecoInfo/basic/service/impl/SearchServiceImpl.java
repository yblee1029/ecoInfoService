package com.ecoInfo.basic.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ecoInfo.basic.domain.Program;
import com.ecoInfo.basic.domain.Region;
import com.ecoInfo.basic.model.SearchKeywordDTO;
import com.ecoInfo.basic.model.SearchKeywordResultDTO;
import com.ecoInfo.basic.model.SearchRegionDTO;
import com.ecoInfo.basic.model.SearchRegionResultDTO;
import com.ecoInfo.basic.repository.ProgramRepository;
import com.ecoInfo.basic.repository.RegionRepository;
import com.ecoInfo.basic.service.SearchService;
import com.ecoInfo.basic.util.CustomException;

@Service
public class SearchServiceImpl implements SearchService {

	@Autowired
	private RegionRepository regionRepository;

	@Autowired
	private ProgramRepository programRepository;

	@Override
	public SearchRegionDTO.Result searchRegion(SearchRegionDTO searchRegionDTO) {

		String regionText = searchRegionDTO.getRegion();

		Region region = regionRepository.findTopByRegionNameContains(regionText);

		return Optional.ofNullable(region).map(r -> {

			SearchRegionDTO.Result result = new SearchRegionDTO.Result();

			List<SearchRegionResultDTO> programDTOList = Optional.ofNullable(r.getPrograms())
					.orElse(Collections.emptySet()).stream().map(p -> {
						return SearchRegionResultDTO.builder()
								.prgm_name(p.getPrgmName())
								.theme(p.getTheme())
								.build();
					}).collect(Collectors.toList());

			result.setRegion(r.getId());
			result.setPrograms(programDTOList);

			return result;
		}).orElseThrow(() -> {
			return new CustomException(HttpStatus.NOT_FOUND);
		});

	}

	@Override
	public SearchKeywordDTO.Result searchKeyword(SearchKeywordDTO searchKeywordDTO) {

		List<Program> programs = programRepository.findProgramByPrgmIntroContains(searchKeywordDTO.getKeyword());

		List<SearchKeywordResultDTO> programDTOList = programs.stream().flatMap(p -> p.getRegionMapping().stream())
				.filter(region -> !StringUtils.isEmpty(region.getRootName())).map(region -> region.getFullRegionName())
				.collect(Collectors.groupingBy(Function.identity())).entrySet().stream().map(entry -> {
					SearchKeywordResultDTO programDTO = new SearchKeywordResultDTO();

					programDTO.setRegion(entry.getKey());
					programDTO.setCount(entry.getValue().size());
					return programDTO;
				}).collect(Collectors.toList());

		SearchKeywordDTO.Result result = new SearchKeywordDTO.Result();

		result.setKeyword(searchKeywordDTO.getKeyword());
		result.setPrograms(programDTOList);

		return result;

	}

	@Override
	public SearchKeywordDTO.CountResult searchKeywordCount(SearchKeywordDTO searchKeywordDTO) {

		Pattern keywordPattern = Pattern.compile(searchKeywordDTO.getKeyword());

		long count = programRepository.findProgramByPrgmDetailsContains(searchKeywordDTO.getKeyword()).stream()
				.map(p -> p.getPrgmDetails()).filter(s -> !StringUtils.isEmpty(s)).mapToLong(s -> {

					Matcher m = keywordPattern.matcher(s);
					long i = 0;
					while (m.find()) {
						i++;
					}
					return i;
				}).sum();

		SearchKeywordDTO.CountResult result = new SearchKeywordDTO.CountResult();
		result.setKeyword(searchKeywordDTO.getKeyword());
		result.setCount(count);

		return result;

	}
}
