package com.goraceloty.offerservice.offer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "offersChange")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OfferChange {
    @Id
    @GeneratedValue( strategy= GenerationType.AUTO )
    private Long offerID;
    private String committedChange;
    private String commitType;
    }
