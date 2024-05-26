//package com.goraceloty.travel_agencyservice;
//import com.goraceloty.travel_agencyservice.travel_agency.control.TransportService;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.AnnotationConfigApplicationContext;
//import java.util.List;
//
//
//public class test_query {
//    public static void main(String[] args) {
//        // Assuming Spring context setup, otherwise you need to configure it or manually instantiate TransportService
//        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
//        TransportService transportService = context.getBean(TransportService.class);
//
//        // Creating a Transport instance and setting properties
//        com.goraceloty.travel_agencyservice.travel_agency.entity.Clients flight = new com.goraceloty.travel_agencyservice.travel_agency.entity.Clients();
//        flight.setCityArrival("Zakynthos (ZTH)");  // Assuming the method to set the city is setCityArrival
//        String cityArrival = flight.getCityArrival();
//        // Call the method from the TransportService
//        List<com.goraceloty.travel_agencyservice.travel_agency.entity.Clients> results = transportService.getTransportByExample(flight);
//
//        // Print the results
//        results.forEach(System.out::println);
//    }
//}
//
//
