package com.ecoInfo.basic.service;

import com.ecoInfo.basic.model.SearchKeywordDTO;
import com.ecoInfo.basic.model.SearchRegionDTO;

public interface SearchService {

	SearchRegionDTO.Result searchRegion(SearchRegionDTO searchRegionDTO);

	SearchKeywordDTO.Result searchKeyword(SearchKeywordDTO searchKeywordDTO);

	SearchKeywordDTO.CountResult searchKeywordCount(SearchKeywordDTO searchKeywordDTO);
}
