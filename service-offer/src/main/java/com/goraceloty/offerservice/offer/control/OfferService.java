package com.goraceloty.offerservice.offer.control;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.goraceloty.offerservice.offer.entity.Offer;

@AllArgsConstructor
@Service
public class OfferService {

    private final OfferRepository offerRepository;

    public String healthCheck() {
        return "OK";
    }

    public Long createOffer(Offer offer) {
        return offerRepository.save(offer).getId();
    }

    public void updateOfferName(Long offerId, String name) {

    }
}
