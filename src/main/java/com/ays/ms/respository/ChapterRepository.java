package com.ays.ms.respository;

import com.ays.ms.model.Chapter;
import com.ays.ms.model.Serie;
import com.ays.ms.respository.springdata.ChapterSpringDataRepository;
import com.ays.ms.respository.springdata.SerieSpringDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ChapterRepository {

    @Autowired
    private ChapterSpringDataRepository chapterSpringDataRepository;

    public List<Chapter> getChapters() { return chapterSpringDataRepository.findAll(); }

    public Chapter getChapter(Long id) {
        Optional<Chapter> optionalChapter = this.chapterSpringDataRepository.findById(id);
        return optionalChapter.orElse(null);
    }

    public Chapter saveChapter(Chapter chapter) { return this.chapterSpringDataRepository.save(chapter); }

    public void deleteChapter(Chapter chapter) { this.chapterSpringDataRepository.delete(chapter); }



}
