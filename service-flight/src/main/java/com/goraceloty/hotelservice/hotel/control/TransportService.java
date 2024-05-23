package com.goraceloty.hotelservice.hotel.control;

//import com.goraceloty.hotelservice.hotel.entity.Hotel;
import com.goraceloty.hotelservice.hotel.entity.Transport;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;


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

    public List<Transport> getTransportByExample(Transport transport) {
        final ExampleMatcher matcher = ExampleMatcher.matchingAll().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        final Example<Transport> example = Example.of(transport, matcher);
        List<Transport> results;
        results = transportRepository.findAll(example);
        return results;
    }

}





