package com.goraceloty.offerservice.offer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "offersChange")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OfferChange {
    @Id
    @GeneratedValue( strategy= GenerationType.IDENTITY)
    private Long changeID;
    private Long offerID;
    private String committedChange;
    private String commitType;
    @Column(nullable = false, updatable = false) // Ensure the timestamp is not updatable
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now(); // Set the timestamp before persisting
    }
    public OfferChange(Long offerID, String committedChange, String commitType) {
        this.offerID = offerID;
        this.committedChange = committedChange;
        this.commitType = commitType;
    }
}
