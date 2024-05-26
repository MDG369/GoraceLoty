package com.goraceloty.hotelservice.hotel.control;

import com.goraceloty.hotelservice.hotel.entity.Availability;
import com.goraceloty.hotelservice.hotel.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, Long> {
    public Optional<Availability> getAvailabilitiesByHotelIDAndDate(Long id, String date);
    public Optional<Availability> getAvailabilityByAvailabilityID(Long id);
    public Optional<Availability> getAvailabilityByDate(String date);
}
