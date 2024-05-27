@RestController
@RequestMapping("/api/transports")
public class TransportController {
    private final TransportService transportService;

    @GetMapping("/{id}/seats")
    public ResponseEntity<SeatDataDTO> getSeatData(@PathVariable Long id) {
        SeatData seatData = transportService.getSeatDataById(id);
        if (seatData == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new SeatDataDTO(seatData.getNumTotalSeats(), seatData.getNumAvailableSeats()));
    }
}


public class SeatDataDTO {
    private int numTotalSeats;
    private int numAvailableSeats;

    public SeatDataDTO(int numTotalSeats, int numAvailableSeats) {
        this.numTotalSeats = numTotalSeats;
        this.numAvailableSeats = numAvailableSeats;
    }

    // Getters and setters
}



@Service
public class OfferService {
    private final RestTemplate restTemplate;

    @Value("${transport.service.url}")
    private String transportServiceUrl;

    public OfferService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public double calculatePrice(Long transportId) {
        SeatDataDTO seatData = restTemplate.getForObject(transportServiceUrl + "/api/transports/" + transportId + "/seats", SeatDataDTO.class);
        return calculatePriceBasedOnSeats(seatData);
    }

    private double calculatePriceBasedOnSeats(SeatDataDTO seatData) {
        // Example calculation: price increases with more available seats
        return seatData.getNumTotalSeats() * 100 - seatData.getNumAvailableSeats() * 50;
    }
}
