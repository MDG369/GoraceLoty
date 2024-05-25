package com.goraceloty.hotelservice.hotel.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TransportReservation")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TransportReservation {
    @Id
    private Long transportID;
    private LocalDateTime bookingTime;
    private Integer seatsNumBookedID;
}
