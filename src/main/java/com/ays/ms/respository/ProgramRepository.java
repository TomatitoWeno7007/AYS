package com.ays.ms.respository;

import com.ays.ms.model.Program;
import com.ays.ms.respository.springdata.ProgramSpringDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProgramRepository {
    @Autowired
    private ProgramSpringDataRepository programSpringDataRepository;

    public List<Program> getPrograms() { return programSpringDataRepository.findAll(); }
}
