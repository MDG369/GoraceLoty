package com.goraceloty.hotelservice.hotel.control;

import com.goraceloty.hotelservice.hotel.entity.Availability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, Long> { }
