package com.goraceloty.hotelservice.hotel.control;

import com.goraceloty.hotelservice.hotel.entity.Hotel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public Hotel getHotelByStars(Integer stars) {
        return hotelRepository.getHotelByStars(stars).orElseThrow();
    }
}
