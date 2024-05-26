package com.goraceloty.hotelservice.hotel.control;

//import com.goraceloty.hotelservice.hotel.entity.Hotel;
import com.goraceloty.hotelservice.hotel.entity.Transport;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.web.client.RestTemplate;


import java.util.List;
import java.util.Optional;

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

    public Transport findTransportByID(Long id) {
        Optional<Transport> transport = transportRepository.findById(id);
        return transport.orElse(null);
    }
//    public List<Transport> getSeatsByExample(Transport transport) {
//        final ExampleMatcher matcher = ExampleMatcher.matchingAll().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
//        final Example<Transport> example = Example.of(transport, matcher);
//        List<Transport> results;
//        results = transportRepository.findById(example);
//        return results;
//    }

   // @Autowired
    //private RestTemplate restTemplate;

//    public void sendData(Transport data) {
//        String url = "http://localhost:8380/api/data";
//        ResponseEntity<String> response = restTemplate.postForEntity(url, "test", String.class);
//        System.out.println("Response from server: " + response.getBody());
//    }


}





