package com.goraceloty.travel_agency_service.travel_agency;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class JsonProcessingExample {

    public static void processJsonData(String url, JsonNode offersArray, String sql, String user, String password) {
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            conn.setAutoCommit(false);
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

            if (offersArray.isArray()) {
                for (JsonNode offer : offersArray) {
                    preparedStatement.setInt(1, offer.get("reservationID").intValue());
                    preparedStatement.setTimestamp(2, Timestamp.valueOf(LocalDateTime.parse(offer.get("startDate").textValue(), dateTimeFormatter)));
                    preparedStatement.setInt(3, offer.get("numAdult").intValue());
                    preparedStatement.setInt(4, offer.get("numChildren").intValue());
                    preparedStatement.setInt(5, offer.get("numOfDays").intValue());
                    preparedStatement.setTimestamp(6, Timestamp.valueOf(LocalDateTime.parse(offer.get("reservationTime").textValue(), dateTimeFormatter)));
                    preparedStatement.setDouble(7, offer.get("adjustedPrice").doubleValue());
                    preparedStatement.setInt(8, offer.get("offerID").intValue());
                    preparedStatement.setInt(9, offer.get("transportID").intValue());
                    preparedStatement.setInt(10, offer.get("hotelID").intValue());

                    preparedStatement.executeUpdate();
                }
                conn.commit();
            }

            preparedStatement.close();
            conn.close();
        } catch (Exception e) {
            System.err.println("Error processing JSON data: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
