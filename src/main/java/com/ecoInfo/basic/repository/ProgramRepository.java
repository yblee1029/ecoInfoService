package com.ecoInfo.basic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecoInfo.basic.domain.Program;

import java.util.List;

@Repository
public interface ProgramRepository extends JpaRepository<Program, String> {

    List<Program> findProgramByPrgmIntroContains(String keyword);

    List<Program> findProgramByPrgmDetailsContains(String keyword);
}
