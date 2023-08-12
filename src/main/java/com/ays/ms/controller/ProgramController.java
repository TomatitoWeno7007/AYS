package com.ays.ms.controller;

import com.ays.ms.model.Program;
import com.ays.ms.service.ProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("program")
public class ProgramController {

    @Autowired
    private ProgramService programService;

    @PostMapping
    public void save() {  }

    @GetMapping
    public List<Program> getPrograms() { return programService.getPrograms(); }

    @GetMapping("/{id}")
    public String getPrograms(@PathVariable("id") long id) { return "Id de la Serie/Pelicula: " + id; }

    @DeleteMapping
    public String deleteProgram(@PathVariable("id") long id) { return "Eliminada la serie/pelicula " + id; }

}
