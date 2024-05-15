package com.goraceloty.hotelservice.hotel.control;

//import com.goraceloty.hotelservice.hotel.entity.Hotel;
import com.goraceloty.hotelservice.hotel.entity.Transport;
import com.goraceloty.hotelservice.hotel.control.TransportService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransportRepository extends JpaRepository<Transport, Long> {
    //public Optional<Transport> getHotelByName(String name);

    //public Optional<Transport> saveTransport(Transport transport);
}


