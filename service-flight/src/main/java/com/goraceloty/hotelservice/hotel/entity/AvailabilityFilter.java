package com.goraceloty.hotelservice.hotel.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AvailabilityFilter {
    private Long hotelID;
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private Integer numOfPeople; // 1-3
}