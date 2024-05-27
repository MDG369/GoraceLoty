package com.goraceloty.travel_agency_service.travel_agency.control;

//import com.goraceloty.travel_agencyservice.travel_agency.entity.travel_agency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TravelAgencyRepository extends JpaRepository<com.goraceloty.travel_agency_service.travel_agency.entity.OfferReservation, Long> {
    //public Optional<Transport> gettravel_agencyByName(String name);

    //public Optional<Transport> saveTransport(Transport transport);

}



