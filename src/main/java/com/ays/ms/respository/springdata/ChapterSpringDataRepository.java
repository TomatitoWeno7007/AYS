package com.ays.ms.respository.springdata;

import com.ays.ms.model.Chapter;
import com.ays.ms.model.Season;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChapterSpringDataRepository extends JpaRepository<Chapter, Long> { }
