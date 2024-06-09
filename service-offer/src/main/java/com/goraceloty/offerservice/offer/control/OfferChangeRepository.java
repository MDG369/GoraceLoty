package com.goraceloty.offerservice.offer.control;

import com.goraceloty.offerservice.offer.entity.OfferChange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferChangeRepository extends JpaRepository<OfferChange, Long> {
}