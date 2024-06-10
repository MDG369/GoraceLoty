package com.goraceloty.offerservice;

import com.fasterxml.jackson.databind.JsonNode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class JsonProcessingOffers {

    public static void processJsonData(String url, JsonNode offerArray, String sql, String user, String password) {
        try {

            // Establish database connection
            Connection conn = DriverManager.getConnection(url, user, password);
            conn.setAutoCommit(false); // Use transaction control

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            System.out.println(preparedStatement);
            System.out.println(offerArray.isArray());

            // Loop through JSON array // id, transportID, hotelID, cityArrival, cityDeparture, dateStart, dateEnd
            if (offerArray.isArray()) {
                System.out.println("początek pętli");
                for (JsonNode transport : offerArray) {
                    preparedStatement.setLong(1, transport.get("id").longValue());
                    preparedStatement.setLong(2, transport.get("transportID").longValue());
                    preparedStatement.setLong(3, transport.get("hotelID").longValue());
                    preparedStatement.setString(4, transport.get("cityArrival").textValue());
                    preparedStatement.setString(5, transport.get("cityDeparture").textValue());
                    preparedStatement.setString(6, transport.get("dateStart").textValue());
                    preparedStatement.setString(7, transport.get("dateEnd").textValue());
                    System.out.println("Executing SQL: " + sql);
                    System.out.println("With parameters: " +
                            transport.get("id").longValue() + ", " +
                            transport.get("transportID").textValue() + ", " +
                            transport.get("hotelID").intValue() + ", " +
                            transport.get("cityArrival").textValue() + ", " +
                            transport.get("cityDeparture").textValue() + ", " +
                            transport.get("dateStart").booleanValue() + ", " +
                            transport.get("dateEnd").textValue());
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