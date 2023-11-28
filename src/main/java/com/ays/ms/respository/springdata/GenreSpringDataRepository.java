package com.ays.ms.respository.springdata;

import com.ays.ms.model.Genres;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenreSpringDataRepository extends JpaRepository<Genres, Long> {

    List<Genres> findByNameIn(List<String> names);

}
