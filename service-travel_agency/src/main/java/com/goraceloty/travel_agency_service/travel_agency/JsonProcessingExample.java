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
//g0r4c3_l0ty
    public static void main(String[] args) {
        String url = "jdbc:postgresql://db_postgres:5432/svc_offers";
        String user = "gl_pg_user";
        String password = "g0r4c3_l0ty";
        String filePath = "C:\\Users\\Admin\\Documents\\GitHub\\GoraceLoty\\data\\offer_reservation_data.json";  // Update the file path to your JSON file
        String sql = "INSERT INTO offer_reservation (reservationid, date_start, num_adult, num_children, num_of_days, reservation_time, adjusted_price, offerid, transportid, hotelid) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                "ON CONFLICT (reservationid) DO UPDATE SET " +
                "date_start = EXCLUDED.date_start, num_adult = EXCLUDED.num_adult, num_children = EXCLUDED.num_children, num_of_days = EXCLUDED.num_of_days, " +
                "reservation_time = EXCLUDED.reservation_time, adjusted_price = EXCLUDED.adjusted_price, offerid = EXCLUDED.offerid, transportid = EXCLUDED.transportid, hotelid = EXCLUDED.hotelid;";

        processJsonData(url, filePath, sql, user, password);
    }

    public static void processJsonData(String url, String filePath, String sql, String user, String password) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            File jsonFile = new File(filePath);
            JsonNode offersArray = mapper.readTree(jsonFile);

            Connection conn = DriverManager.getConnection(url, user, password);
            conn.setAutoCommit(false);
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            if (offersArray.isArray()) {
                for (JsonNode offer : offersArray) {
                    preparedStatement.setInt(1, offer.get("reservationID").intValue());  // Handle reservationID
                    preparedStatement.setTimestamp(2, Timestamp.valueOf(LocalDateTime.parse(offer.get("dateStart").textValue(), dateTimeFormatter)));
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
