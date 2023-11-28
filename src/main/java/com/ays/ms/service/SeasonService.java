package com.ays.ms.service;

import com.ays.ms.respository.SeasonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeasonService {

    @Autowired
    SeasonRepository seasonRepository;

}
