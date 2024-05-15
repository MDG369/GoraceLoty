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

    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/svc_flight";
        String user = "gl_pg_user";
        String password = "g0r4c3_l0ty";
        String filePath = "C:\\Users\\Admin\\Documents\\GitHub\\GoraceLoty\\service-flight\\InitialData.json";
        String sql = "INSERT INTO transports (id, name, num_total_seats, num_available_seats, num_base_price, departure_location, arrival_location, date_departure, date_arrival) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) ON CONFLICT (id) DO UPDATE SET name = EXCLUDED.name, num_total_seats = EXCLUDED.num_total_seats, num_available_seats = EXCLUDED.num_available_seats, num_base_price = EXCLUDED.num_base_price, departure_location = EXCLUDED.departure_location, arrival_location = EXCLUDED.arrival_location, date_departure = EXCLUDED.date_departure, date_arrival = EXCLUDED.date_arrival;";

        processJsonData(url, filePath, sql, user, password);
    }

    public static void processJsonData(String url, String filePath, String sql, String user, String password) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            File jsonFile = new File(filePath);
            JsonNode transportsArray = mapper.readTree(jsonFile);

            // Establish database connection
            Connection conn = DriverManager.getConnection(url, user, password);
            conn.setAutoCommit(false); // Use transaction control

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");

            // Loop through JSON array
            if (transportsArray.isArray()) {
                for (JsonNode transport : transportsArray) {
                    preparedStatement.setLong(1, transport.get("id").longValue());
                    preparedStatement.setString(2, transport.get("name").textValue());
                    preparedStatement.setInt(3, transport.get("numTotalSeats").intValue());
                    preparedStatement.setInt(4, transport.get("numAvailableSeats").intValue());
                    preparedStatement.setInt(5, transport.get("numBasePrice").intValue());
                    preparedStatement.setString(6, transport.get("departureLocation").textValue());
                    preparedStatement.setString(7, transport.get("arrivalLocation").textValue());
                    preparedStatement.setTimestamp(8, Timestamp.valueOf(LocalDateTime.parse(transport.get("dateDeparture").textValue(), formatter)));
                    preparedStatement.setTimestamp(9, Timestamp.valueOf(LocalDateTime.parse(transport.get("dateArrival").textValue(), formatter)));

                    // Execute upsert
                    preparedStatement.executeUpdate();
                }
                conn.commit(); // Commit all changes
            }

            // Clean up
            preparedStatement.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
