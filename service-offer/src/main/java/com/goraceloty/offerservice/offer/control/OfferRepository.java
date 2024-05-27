package com.goraceloty.offerservice.offer.control;

import com.goraceloty.offerservice.offer.entity.OfferFilter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferRepository extends JpaRepository<OfferFilter, Long> {
}
