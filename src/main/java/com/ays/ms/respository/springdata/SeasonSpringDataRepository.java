package com.ays.ms.respository.springdata;

import com.ays.ms.model.Season;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeasonSpringDataRepository extends JpaRepository<Season, Long> { }
