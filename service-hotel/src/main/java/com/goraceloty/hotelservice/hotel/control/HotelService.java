package com.goraceloty.hotelservice.hotel.control;

import com.goraceloty.hotelservice.hotel.entity.Hotel;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class HotelService {

    private final HotelRepository hotelRepository;

    public List<Hotel> getHotels() {
        return hotelRepository.findAll();
    }

    public void addHotel(Hotel hotel) {
        hotelRepository.save(hotel);
    }

    public void removeHotelById(Long id) {
        hotelRepository.delete(hotelRepository.findById(id).orElseThrow());
    }

//    public Hotel getHotelByStars(Integer stars) {
//        return hotelRepository.getHotelByStars(stars).orElseThrow();
//    }

    public Hotel findHotelByID(Long id) {
        Optional<Hotel> transport = hotelRepository.findById(id);
        return transport.orElse(null);
    }

    public List<Hotel> getHotelsByExample(Hotel hotel) {
        final ExampleMatcher matcher = ExampleMatcher.matchingAll().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        final Example<Hotel> example = Example.of(hotel, matcher);
        List<Hotel> results;
        results = hotelRepository.findAll(example);

        return results;
    }
}