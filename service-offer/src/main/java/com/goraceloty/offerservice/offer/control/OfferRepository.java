package com.goraceloty.offerservice.offer.control;

import com.goraceloty.offerservice.offer.entity.Offer;
import com.goraceloty.offerservice.offer.entity.OfferChange;
import com.goraceloty.offerservice.offer.entity.OfferFilter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {
    static void save(OfferChange offerChange) {
        
    }
}
