package com.goraceloty.offerservice.offer.control;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.goraceloty.offerservice.offer.entity.Offer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@AllArgsConstructor
@Service
public class OfferService {

    private final OfferRepository offerRepository;

    public String GetHotels(Offer offer) throws IOException {
        URL url;
        System.out.println("###############" + offer.getCountry());
        if(offer.getCountry() != null) {
            url = new URL("http://10.10.1.1:8080/hotels/matching?country=" + offer.getCountry());
        }
        else {
            url = new URL("http://10.10.1.1:8080/hotels");
        }
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return response.toString();
        }
        return "Connection error to hotels";
    }

    public String GetTransports(Offer offer) throws IOException {
        URL url;
        System.out.println("###############" + offer.getCountry());
        if(offer.getCountry() != null) {
            url = new URL("http://10.10.1.3:8080/transports");
        }
        else {
            url = new URL("http://10.10.1.3:8080/transports");
        }
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        System.out.println("############### code: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return response.toString();
        }
        return "Connection error to transports";
    }

    public String healthCheck() {
        return "OK";
    }

    public Long createOffer(Offer offer) {
        return offerRepository.save(offer).getId();
    }

    public void updateOfferName(Long offerId, String name) {

    }
}
