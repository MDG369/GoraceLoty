//package com.goraceloty.offerservice;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import java.io.File;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.Timestamp;
//import java.time.Instant;
//import java.time.LocalDateTime;
//import java.time.ZoneId;
//import java.time.format.DateTimeFormatter;
//
//public class JsonProcessingExample {
//
//    public static void main(String[] args) {
//        String url = "jdbc:postgresql://localhost:5432/svc_offer";
//        String user = "gl_pg_user";
//        String password = "g0r4c3_l0ty";
//        String filePath = "C:\\Users\\Admin\\Documents\\GitHub\\GoraceLoty\\data\\offers_copy.json";
//        // Added 'offername' to the SQL statement
//        String sql = "INSERT INTO offers (offerid,hotelid, city, datestart, dateend, numofpeople, offername) " +
//                "VALUES (?, ?, ?, ?, ?, ?, ?, ?) " +
//                "ON CONFLICT (offerid) DO UPDATE SET " +
//                "transportid = EXCLUDED.transportid, hotelid = EXCLUDED.hotelid, city = EXCLUDED.city, " +
//                "datestart = EXCLUDED.datestart, dateend = EXCLUDED.dateend, numofpeople = EXCLUDED.numofpeople, " +
//                "offername = EXCLUDED.offername;";
//
//        processJsonData(url, filePath, sql, user, password);
//    }
//
//    public static void processJsonData(String url, String filePath, String sql, String user, String password) {
//        try {
//            ObjectMapper mapper = new ObjectMapper();
//            File jsonFile = new File(filePath);
//            JsonNode offersArray = mapper.readTree(jsonFile);
//
//            Connection conn = DriverManager.getConnection(url, user, password);
//            conn.setAutoCommit(false); // Use transaction control
//
//            PreparedStatement preparedStatement = conn.prepareStatement(sql);
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//            System.out.println(offersArray.isArray());
//
//            if (offersArray.isArray()) {
//                for (JsonNode offer : offersArray) {
//                    Long transportId = getLongValueFromJsonNode(transport, "transportID");
//                    LocalDateTime dateStart = convertTimestampToLocalDateTime(transport.get("dateStart").asLong());
//                    LocalDateTime dateEnd = convertTimestampToLocalDateTime(transport.get("dateEnd").asLong());
//
//                    preparedStatement.setLong(1, offer.get("offerID").longValue());
//                    //preparedStatement.setLong(2, offer.get("transportID").longValue());
//                    preparedStatement.setLong(3, offer.get("hotelID").longValue());
//                    preparedStatement.setString(4, offer.get("city").textValue());
//                    preparedStatement.setTimestamp(5, Timestamp.valueOf(LocalDateTime.parse(offer.get("dateStart").textValue(), formatter)));
//                    preparedStatement.setTimestamp(6, Timestamp.valueOf(LocalDateTime.parse(offer.get("dateEnd").textValue(), formatter)));
//                    preparedStatement.setInt(7, offer.get("numOfPeople").intValue());
//                    preparedStatement.setString(8, offer.get("offerName").textValue());  // Handling offerName
//
//                    preparedStatement.executeUpdate();
//                }
//                conn.commit(); // Commit all changes
//            }
//
//            preparedStatement.close();
//            conn.close();
//        } catch (Exception e) {
//            System.out.println("Error processing JSON data: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
//}
