package com.ays.ms.respository;

import com.ays.ms.model.Card;
import com.ays.ms.respository.springdata.CardSpringDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public class CardRepository {

    @Autowired
    private CardSpringDataRepository cardSpringDataRepository;

    public Card getCardById(long id) {
        return cardSpringDataRepository.findById(id).orElse(null);
    }

    public Card saveCard(Card card) {
        return cardSpringDataRepository.save(card);
    }

}
