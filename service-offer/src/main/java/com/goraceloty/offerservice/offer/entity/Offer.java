package com.goraceloty.offerservice.offer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "offers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Offer {
    @Id
    @GeneratedValue( strategy= GenerationType.AUTO )
    private Long id;
    private Long transportID;
    private Long hotelID;
    private String cityArrival;
    private String cityDeparture;
    private String dateStart;
    private String dateEnd;
}
