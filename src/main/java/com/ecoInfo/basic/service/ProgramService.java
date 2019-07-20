package com.ecoInfo.basic.service;

import java.io.InputStream;

import com.ecoInfo.basic.model.ProgramDTO;

public interface ProgramService {

    void importCSV(InputStream is);

    ProgramDTO saveProgram(ProgramDTO programDTO);

    ProgramDTO selectProgram(String programId);
}
