package com.goraceloty.hotelservice.hotel.control;

import com.goraceloty.hotelservice.hotel.entity.Availability;
import com.goraceloty.hotelservice.hotel.entity.Hotel;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Log
@AllArgsConstructor
@Service
public class HotelService {

    private final HotelRepository hotelRepository;
    private final AvailabilityRepository availabilityRepository;

    public List<Hotel> getHotels() {
        log.info("Getting all hotels");
        return hotelRepository.findAll();
    }

    public void addHotel(Hotel hotel) {
        hotelRepository.save(hotel);
    }

    public void removeHotelById(Long id) {
        hotelRepository.delete(hotelRepository.findById(id).orElseThrow());
    }

    public Hotel findHotelByID(Long id) {
        Optional<Hotel> transport = hotelRepository.findById(id);
        return transport.orElse(null);
    }

    public Hotel getHotelByStars(Integer stars) {
        return hotelRepository.getHotelByStandard(stars).orElseThrow();
    }

    public List<Hotel> getHotelsByExample(Hotel hotel) {
        log.info("Getting by Example");
        final ExampleMatcher matcher = ExampleMatcher.matchingAll().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        final Example<Hotel> example = Example.of(hotel, matcher);
        List<Hotel> results;
        results = hotelRepository.findAll(example);
        log.info("Results :" + results);
        return results;
    }

    public void bookHotelRooms(Long id, Integer numberOfSingleRooms,
                               Integer numberOfDoubleRooms, Integer numberOfTripleRooms,
                               Integer numberOfStudios, Integer numberOfApartments,
                               LocalDate startDate, Integer numOfDays) throws IllegalArgumentException, NoSuchElementException {
        /*
            Book hotel rooms, if there are not enough available rooms throws IllegalArgumentException
        */
        for (int i = 0; i < numOfDays; i++) {
            Availability availability = availabilityRepository.getAvailabilityByHotelIDAndDate(id, startDate).orElseThrow();
            availability.setNumOfAvSingleRooms(availability.getNumOfAvSingleRooms() - numberOfSingleRooms);
            availability.setNumOfAvDoubleRooms(availability.getNumOfAvDoubleRooms() - numberOfDoubleRooms);
            availability.setNumOfAvTripleRooms(availability.getNumOfAvTripleRooms() - numberOfTripleRooms);
            availability.setNumOfAvApartments(availability.getNumOfAvApartments() - numberOfApartments);
            availability.setNumOfAvStudios(availability.getNumOfAvStudios() - numberOfStudios);
            if (availability.getNumOfAvSingleRooms() < 0 || availability.getNumOfAvDoubleRooms() < 0 ||
                    availability.getNumOfAvTripleRooms() < 0 || availability.getNumOfAvApartments() < 0 ||
                    availability.getNumOfAvStudios() < 0) {
                    throw new IllegalArgumentException("Number of booked rooms exceeds the number of available rooms");
            }
            availabilityRepository.save(availability);
        }
    }


    public void cancelBookingHotelRooms(Long id, Integer numberOfSingleRooms,
                                        Integer numberOfDoubleRooms, Integer numberOfTripleRooms,
                                        Integer numberOfStudios, Integer numberOfApartments,
                                        LocalDate startDate, Integer numOfDays) throws IllegalArgumentException, NoSuchElementException {
        /*
            Cancel the booking of hotel rooms
        */
        for (int i = 0; i < numOfDays; i++) {
            Availability availability = availabilityRepository.getAvailabilityByHotelIDAndDate(id, startDate.plusDays(i)).orElseThrow();
            availability.setNumOfAvSingleRooms(availability.getNumOfAvSingleRooms() + numberOfSingleRooms);
            availability.setNumOfAvDoubleRooms(availability.getNumOfAvDoubleRooms() + numberOfDoubleRooms);
            availability.setNumOfAvTripleRooms(availability.getNumOfAvTripleRooms() + numberOfTripleRooms);
            availability.setNumOfAvApartments(availability.getNumOfAvApartments() + numberOfApartments);
            availability.setNumOfAvStudios(availability.getNumOfAvStudios() + numberOfStudios);
            availabilityRepository.save(availability);
        }
    }

    public String generateHotelAvailability() {
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("uuuu-MM-dd");
        Availability tmpAv;
        List<Hotel> hotels = hotelRepository.findAll();

        if (hotels.isEmpty()) {
            return "No hotels found!";
        }

        for (Hotel hotel : hotels) {
            if (hotel.getHotelID() == null) {
                return "Hotel ID is null!";
            }
            for (int i = 0; i < 30; i++) {
                tmpAv = new Availability();
                tmpAv.setHotelID(hotel.getHotelID());
                tmpAv.setDate(LocalDate.now().plusDays(i));
                tmpAv.setNumOfAvSingleRooms(15);
                tmpAv.setNumOfAvDoubleRooms(10);
                tmpAv.setNumOfAvTripleRooms(0);
                tmpAv.setNumOfAvApartments(5);
                tmpAv.setNumOfAvStudios(5);
                availabilityRepository.save(tmpAv);
            }
        }
        return "Update successful!";
    }
}