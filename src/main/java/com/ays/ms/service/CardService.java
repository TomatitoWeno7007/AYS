package com.ays.ms.service;

import com.ays.ms.model.Card;
import com.ays.ms.respository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;

    public Card getCardById(long id) {
        return cardRepository.getCardById(id);
    }

    public Card createCard() {
        Card card = new Card();
        return cardRepository.saveCard(card);
    }

    public void saveCard(Card card) {
        cardRepository.saveCard(card);
    }

}
