import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.lang.System.*;

public class JsonProcessingExample {

    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/svc_flight";
        String user = "gl_pg_user";
        String password = "g0r4c3_l0ty";
        //String filePath = "C:\\Users\\Admin\\Documents\\GitHub\\GoraceLoty\\service-flight\\InitialData.json";
        String filePath = "..\\data\\transport_final_clean.json";
        String sql = "INSERT INTO transports(transportid, type_of_transport, num_total_seats, num_available_seats, num_base_price, city_departure, city_arrival, date_departure, date_arrival) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                "ON CONFLICT (transportid) DO UPDATE SET " +
                "type_of_transport = EXCLUDED.type_of_transport, " +
                "num_total_seats = EXCLUDED.num_total_seats, " +
                "num_available_seats = EXCLUDED.num_available_seats, " +
                "num_base_price = EXCLUDED.num_base_price, " +
                "city_departure = EXCLUDED.city_departure, " +
                "city_arrival = EXCLUDED.city_arrival, " +
                "date_departure = EXCLUDED.date_departure, " +
                "date_arrival = EXCLUDED.date_arrival;";


        processJsonData(url, filePath, sql, user, password);
    }

    public static void processJsonData(String url, String filePath, String sql, String user, String password) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            File jsonFile = new File(filePath);
            JsonNode transportsArray = mapper.readTree(jsonFile);
            //System.out.println(transportsArray.toString());


            // Establish database connection
            Connection conn = DriverManager.getConnection(url, user, password);
            conn.setAutoCommit(false); // Use transaction control

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            System.out.println(preparedStatement);
            //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
            System.out.println(transportsArray.isArray());

            // Loop through JSON array
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

            // Clean up
            preparedStatement.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("Nie wyszło");
            e.printStackTrace();
        }
    }
}
