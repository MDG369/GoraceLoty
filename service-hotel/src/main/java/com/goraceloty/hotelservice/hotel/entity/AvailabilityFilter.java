package com.goraceloty.hotelservice.hotel.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AvailabilityFilter {
    private Long hotelID;
    private String dateStart;
    private String dateEnd;
    private Integer numOfPeople; // 1-3
}