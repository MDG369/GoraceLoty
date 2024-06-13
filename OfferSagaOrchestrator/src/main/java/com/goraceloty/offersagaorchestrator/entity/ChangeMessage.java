package com.goraceloty.offersagaorchestrator.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChangeMessage {
    private String content;
    private Long hotelId;
    private Long transportId;
    private Long offerId;
}
