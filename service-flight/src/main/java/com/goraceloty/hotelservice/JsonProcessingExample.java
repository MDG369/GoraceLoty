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

public class JsonProcessingExample {

    public static void processJsonData(String url, JsonNode transportsArray, String sql, String user, String password) {
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            conn.setAutoCommit(false);
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");

            if (transportsArray.isArray()) {
                System.out.println("początek pętli");
                for (JsonNode transport : transportsArray) {
                    preparedStatement.setLong(1, transport.get("transportID").longValue());
                    preparedStatement.setString(2, transport.get("typeOfTransport").textValue());
                    preparedStatement.setInt(3, transport.get("numTotalSeats").intValue());
                    preparedStatement.setInt(4, transport.get("numAvailableSeats").intValue());
                    preparedStatement.setInt(5, transport.get("numBasePrice").intValue());
                    preparedStatement.setString(6, transport.get("cityDeparture").textValue());
                    preparedStatement.setString(7, transport.get("cityArrival").textValue());
                    preparedStatement.setTimestamp(8, Timestamp.valueOf(LocalDateTime.parse(transport.get("dateDeparture").textValue(), formatter)));
                    preparedStatement.setTimestamp(9, Timestamp.valueOf(LocalDateTime.parse(transport.get("dateArrival").textValue(), formatter)));
                    System.out.println("Executing SQL: " + sql);
                    System.out.println("With parameters: " +
                            transport.get("transportID").longValue() + ", " +
                            transport.get("typeOfTransport").textValue() + ", " +
                            transport.get("numTotalSeats").intValue() + ", " +
                            transport.get("numAvailableSeats").intValue() + ", " +
                            transport.get("numBasePrice").intValue() + ", " +
                            transport.get("cityDeparture").textValue() + ", " +
                            transport.get("cityArrival").textValue() + ", " +
                            transport.get("dateDeparture").textValue() + ", " +
                            transport.get("dateArrival").textValue());
                    // Execute upsert
                    preparedStatement.executeUpdate();
                }
                conn.commit(); // Commit all changes
            }

            preparedStatement.close();
            conn.close();
        } catch (Exception e) {
            System.err.println("Error processing JSON data: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
