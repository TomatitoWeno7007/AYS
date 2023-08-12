package com.ays.ms.respository.springdata;

import com.ays.ms.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SerieSpringDataRepository extends JpaRepository<Serie, Long> {
}
