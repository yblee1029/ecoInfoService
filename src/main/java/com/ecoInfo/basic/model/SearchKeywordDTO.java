package com.ecoInfo.basic.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchKeywordDTO {

    private String keyword;

    
    @Data
    @EqualsAndHashCode(callSuper=false)
    @NoArgsConstructor
    public static class Result extends SearchKeywordDTO {

        List<SearchKeywordResultDTO> programs;
    }
    
    @Data
    @EqualsAndHashCode(callSuper=false)
    @NoArgsConstructor
    public static class CountResult extends SearchKeywordDTO {
    	
    	long count;
    }
}
