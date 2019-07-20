package com.ecoInfo.basic.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecoInfo.basic.model.SearchKeywordDTO;
import com.ecoInfo.basic.model.SearchRegionDTO;
import com.ecoInfo.basic.service.SearchService;

@RestController
@RequestMapping("/search")
public class SearchController {

	@Autowired
    private SearchService searchService;

    /**
      * @Method Name : searchRegion
      * @작성일 : 2019. 7. 20.
      * @작성자 : yblee
      * @변경이력 : 
      * @Method 설명 : 생태 관광지 중에 서비스 지역 컬럼에서 특정 지역에서 진행되는 프로그램명과 테마를 출력하는 API
      * @param searchRegionDTO
      * @return
      */
    @PostMapping("/region")
    public SearchRegionDTO.Result searchRegion(@RequestBody @Valid SearchRegionDTO searchRegionDTO)  {
        return searchService.searchRegion(searchRegionDTO);
    }

    /**
      * @Method Name : searchKeyword
      * @작성일 : 2019. 7. 20.
      * @작성자 : yblee
      * @변경이력 : 
      * @Method 설명 : 생태 정보 데이터에 프로그램 소개 컬럼에서 특정 문자열이 포함된 레코드에서 서비스 지역 개수를 세어 출력하는 API
      * @param searchKeywordDTO
      * @return
      * @throws IOException
      */
    @PostMapping("/keyword")
    public SearchKeywordDTO.Result searchKeyword(@RequestBody @Valid SearchKeywordDTO searchKeywordDTO) throws IOException {
        return searchService.searchKeyword(searchKeywordDTO);
    }

    /**
      * @Method Name : searchKeywordCount
      * @작성일 : 2019. 7. 20.
      * @작성자 : yblee
      * @변경이력 : 
      * @Method 설명 : 모든 레코드에 프로그램 상세 정보를 읽어와서 입력 단어의 출현빈도수를 계산하여 출력 하는 API
      * @param searchKeywordDTO
      * @return
      * @throws IOException
      */
    @PostMapping("/keywordCount")
    public SearchKeywordDTO.CountResult searchKeywordCount(@RequestBody @Valid SearchKeywordDTO searchKeywordDTO) throws IOException {
        return searchService.searchKeywordCount(searchKeywordDTO);
    }

}
