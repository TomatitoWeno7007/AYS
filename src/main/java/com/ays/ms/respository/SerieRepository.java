package com.ays.ms.respository;

import com.ays.ms.model.Serie;
import com.ays.ms.respository.springdata.SerieSpringDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SerieRepository {

    @Autowired
    private SerieSpringDataRepository serieSpringDataRepository;

    public List<Serie> getSeries() { return serieSpringDataRepository.findAll(); }

}
