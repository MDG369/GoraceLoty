
package com.goraceloty.offerservice.offer.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
public class OfferFilter {
    @Id
    private Long id;
    private String city;
    private String dateStart;
    private String dateEnd;
    private Integer numOfPeople;
}
