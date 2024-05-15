package com.goraceloty.hotelservice.hotel.control;

//import com.goraceloty.hotelservice.hotel.entity.Hotel;
import com.goraceloty.hotelservice.hotel.entity.Transport;
import com.goraceloty.hotelservice.hotel.control.TransportRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@AllArgsConstructor
@Service

public class TransportService {
    private final TransportRepository transportRepository;

    public List<Transport> getTransport() {
        return transportRepository.findAll();
    }

    public void addTransport(Transport transport) {
        transportRepository.save(transport);
    }

    public void removeTransportById(Long id) {
        transportRepository.delete(transportRepository.findById(id).orElseThrow());
    }

//    public Transport saveTransport(Transport transport) {
//        return transportRepository.save(transport);
//    }

//    public Transport getHotelByStars(Integer stars) {
//        return transportRepository.saveTransport(stars).orElseThrow();
//    }
}
