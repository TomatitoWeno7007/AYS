package com.ays.ms.service;

import com.ays.ms.model.Serie;
import com.ays.ms.respository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SerieService {

    @Autowired
    private SerieRepository serieRepository;

    public List<Serie> getSeries() { return serieRepository.getSeries(); }

    public Serie getSerie(Long id) {
        return this.serieRepository.getSerie(id);
    }

    public Long getNumberSeries () {
        return this.serieRepository.getNumberSeries();
    }

}
