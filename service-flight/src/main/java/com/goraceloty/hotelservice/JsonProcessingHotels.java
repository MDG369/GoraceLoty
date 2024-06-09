package com.goraceloty.hotelservice;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.time.format.DateTimeFormatter;

public class JsonProcessingHotels {

    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/svc_hotel";
        String user = "gl_pg_user";
        String password = "g0r4c3_l0ty";
        String filePath = "..\\data\\hotels_final_clean.json";
        String sql = "INSERT INTO hotels(hotelid, hotel_name, standard, country, city, children_allowed, address ) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?) " +
                "ON CONFLICT (HotelID) DO UPDATE SET " +
                "hotel_name = EXCLUDED.hotel_name, " +
                "standard = EXCLUDED.standard, " +
                "country = EXCLUDED.country, " +
                "city = EXCLUDED.city, " +
                "children_allowed = EXCLUDED.children_allowed, " +
                "address = EXCLUDED.address;";


        processJsonData(url, filePath, sql, user, password);
    }

    public static void processJsonData(String url, String filePath, String sql, String user, String password) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            File jsonFile = new File(filePath);
            JsonNode transportsArray = mapper.readTree(jsonFile);
            System.out.println(transportsArray.toString());


            // Establish database connection
            Connection conn = DriverManager.getConnection(url, user, password);
            conn.setAutoCommit(false); // Use transaction control

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            System.out.println(preparedStatement);
            //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
            System.out.println(transportsArray.isArray());

            // Loop through JSON array // HotelID, hotelName, standard, country, city, childrenAllowed, address
            if (transportsArray.isArray()) {
                System.out.println("początek pętli");
                for (JsonNode transport : transportsArray) {
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
