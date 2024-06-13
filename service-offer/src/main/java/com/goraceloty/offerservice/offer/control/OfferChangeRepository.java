package com.goraceloty.offerservice.offer.control;

import com.goraceloty.offerservice.offer.entity.OfferChange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferChangeRepository extends JpaRepository<OfferChange, Long> {
    List<OfferChange> findTop10OByOrderByCreatedAtDesc();
}