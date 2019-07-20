package com.ecoInfo.basic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecoInfo.basic.domain.Region;

@Repository
public interface RegionRepository extends JpaRepository<Region, String> {

    Region findTopByRegionNameEqualsAndRootNameEquals(String regionName, String rootName);

    Region findTopByRegionNameContains(String regionName);
}
