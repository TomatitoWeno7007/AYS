package com.ays.ms.respository.springdata;

import com.ays.ms.model.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmSpringDataRepository extends JpaRepository<Film, Long> {

}
