package com.goraceloty.apigateway.hotels.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Hotel {
    public Long hotelID;
    private String hotelName;
    private Integer standard;
    private String country;
    private String city;
    private Boolean childrenAllowed;
    private String address;
}
