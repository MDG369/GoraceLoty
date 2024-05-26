package com.goraceloty.hotelservice.hotel.control;

import com.goraceloty.hotelservice.hotel.entity.Availability;
import com.goraceloty.hotelservice.hotel.entity.AvailabilityFilter;
import com.goraceloty.hotelservice.hotel.entity.Hotel;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class AvailabilityService {

    @Autowired
    private final AvailabilityRepository availabilityRepository;

    public Boolean getAvailability(AvailabilityFilter filter) {
        if(filter.getDateEnd() == null || filter.getDateStart() == null ||
                filter.getHotelID() == null || filter.getNumOfPeople() == null) {
            System.out.println("Not enough parameters");
            return false;
        }

        if(filter.getNumOfPeople() > 3) {
            System.out.println("Wrong number of people (should be 1-3)");
            return false;
        }

        Optional<Availability> start;
        Optional<Availability> end;

        System.out.println(filter.getHotelID() + " " + filter.getDateStart() + " " + filter.getDateEnd());
        start = availabilityRepository.getAvailabilitiesByHotelIDAndDate(filter.getHotelID(), filter.getDateStart());
        end = availabilityRepository.getAvailabilitiesByHotelIDAndDate(filter.getHotelID(), filter.getDateEnd());

        if(start.isPresent() && end.isPresent()) {
            System.out.println("start id: " + start.get().getAvailabilityID() + " end id: " + end.get().getAvailabilityID());
        }
        else {
            System.out.println("start or end not present");
            return false;
        }

        Long idStart = start.get().getAvailabilityID();
        Long idEnd = end.get().getAvailabilityID();
        Integer avSpaces = 0;

        if(idStart > idEnd) {
            System.out.println("wrong range");
            return false;
        }

        while(idStart <= idEnd) {
            if(filter.getNumOfPeople() == 1) {
                avSpaces = availabilityRepository.getAvailabilityByAvailabilityID(idStart).get().getNumOfAvSingleRooms();
                if(avSpaces < 1) {
                    System.out.println("Not enough single rooms");
                    return false;
                }
            }
            else if(filter.getNumOfPeople() == 2) {
                avSpaces = availabilityRepository.getAvailabilityByAvailabilityID(idStart).get().getNumOfAvDoubleRooms();
                if(avSpaces < 1) {
                    System.out.println("Not enough double rooms");
                    return false;
                }
            }
            else if(filter.getNumOfPeople() == 3) {
                avSpaces = availabilityRepository.getAvailabilityByAvailabilityID(idStart).get().getNumOfAvTripleRooms();
                if(avSpaces < 1) {
                    System.out.println("Not enough triple rooms");
                    return false;
                }
            }
            System.out.println(avSpaces);
            idStart += 1;
        }

        //System.out.println(start.get().getAvailabilityID());
        //System.out.println(end.get().getAvailabilityID());
        return true;
    }
}
