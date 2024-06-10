package com.goraceloty.hotelservice;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class JsonProcessingHotels {

    public static void processJsonData(String url, JsonNode hotelsArray, String sql, String user, String password) {
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            conn.setAutoCommit(false);
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

            // Loop through JSON array // HotelID, hotelName, standard, country, city, childrenAllowed, address
            if (hotelsArray.isArray()) {
                System.out.println("początek pętli");
                for (JsonNode transport : hotelsArray) {
                    preparedStatement.setLong(1, transport.get("HotelID").longValue());
                    preparedStatement.setString(2, transport.get("hotelName").textValue());
                    preparedStatement.setInt(3, transport.get("standard").intValue());
                    preparedStatement.setString(4, transport.get("country").textValue());
                    preparedStatement.setString(5, transport.get("city").textValue());
                    preparedStatement.setBoolean(6, transport.get("childrenAllowed").booleanValue());
                    preparedStatement.setString(7, transport.get("address").textValue());
                    System.out.println("Executing SQL: " + sql);
                    System.out.println("With parameters: " +
                            transport.get("HotelID").longValue() + ", " +
                            transport.get("hotelName").textValue() + ", " +
                            transport.get("standard").intValue() + ", " +
                            transport.get("country").textValue() + ", " +
                            transport.get("city").textValue() + ", " +
                            transport.get("childrenAllowed").booleanValue() + ", " +
                            transport.get("address").textValue());
                    // Execute upsert
                    preparedStatement.executeUpdate();
                }
                conn.commit(); // Commit all changes
            }

            // Clean up
            preparedStatement.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("Nie wyszło");
            e.printStackTrace();
        }
    }
}