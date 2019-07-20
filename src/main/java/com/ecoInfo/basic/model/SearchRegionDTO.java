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
public class SearchRegionDTO {

    private String region;

    
    @Data
    @EqualsAndHashCode(callSuper=false)
    @NoArgsConstructor
    public static class Result extends SearchRegionDTO {

        List<SearchRegionResultDTO> programs;
    }
}
