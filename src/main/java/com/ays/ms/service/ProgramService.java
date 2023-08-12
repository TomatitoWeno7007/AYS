package com.ays.ms.service;

import com.ays.ms.model.Program;
import com.ays.ms.respository.ProgramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProgramService {
    @Autowired
    private ProgramRepository programRepository;

    public List<Program> getPrograms() { programRepository.getPrograms(); }
}
