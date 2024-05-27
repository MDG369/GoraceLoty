package com.goraceloty.travel_agency_service.travel_agency.entity;

import com.goraceloty.travel_agency_service.saga.entity.ReservationRequest;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "offerReservation")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OfferReservation {
    @Id
    @GeneratedValue( strategy= GenerationType.AUTO )
    private Long reservationID;
    private LocalDateTime startDate;
    private Integer numAdult;
    private Integer numChildren;
    private Integer numOfDays;
    private Timestamp reservationTime;
    private Double adjustedPrice;
    private Long offerID;
    private Long hotelID;
    private Long transportID;
    private Boolean isPaid;
    @OneToOne
    private Client client;

    public void createOfferReservationFromReservationRequest(ReservationRequest reservationRequest) {
        this.setHotelID(reservationRequest.getHotelID());
        this.setTransportID(reservationRequest.getTransportID());
        this.setOfferID(reservationRequest.getOfferID());
        this.setReservationTime(new Timestamp(System.currentTimeMillis()));
        this.setStartDate(reservationRequest.getStartDate());
        this.setNumAdult(reservationRequest.getNumOfAdults());
        this.setNumChildren(reservationRequest.getNumOfChildren());
        this.setNumOfDays(reservationRequest.getNumOfDays());
        this.setIsPaid(false);
    }
}

