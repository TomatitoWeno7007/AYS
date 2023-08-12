package com.ays.ms.respository.springdata;

import com.ays.ms.model.Program;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgramSpringDataRepository extends JpaRepository<Program, Long> {

}
