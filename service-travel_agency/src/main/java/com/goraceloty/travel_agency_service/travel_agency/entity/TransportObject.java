package com.goraceloty.travel_agency_service.travel_agency.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class TransportObject {
    private String content;

    @Override
    public String toString() {
        return "DataObject{" +
                "content='" + content + '\'' +
                '}';
    }
}
