package com.goraceloty.offerservice.offer.control;

import lombok.AllArgsConstructor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import com.goraceloty.offerservice.offer.entity.OfferFilter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@AllArgsConstructor
@Service
public class OfferService {

    private final OfferRepository offerRepository;

    public String GetHotels(OfferFilter offerFilter) throws IOException, JSONException {
        URL url;
        System.out.println("###############" + offerFilter.getCity());
        if(offerFilter.getCity() != null) {
            url = new URL("http://10.10.1.1:8080/hotels/matching?city=" + offerFilter.getCity().replaceAll(" ", "%20"));
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
        return "[\"hotel\" : none]";
    }

    public String GetTransports(OfferFilter offerFilter) throws IOException {
        URL url;
        System.out.println("###############" + offerFilter.getCity());
        if(offerFilter.getCity() != null) {
            url = new URL("http://10.10.1.3:8080/transports/matching?cityArrival=" + offerFilter.getCity().replaceAll(" ", "%20"));
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
        return "[\"transport\" : none]";
    }

    public String BuildOffer(OfferFilter offerFilter) {
        return "OK";
    }

    public Long createOffer(OfferFilter offer) {
        return offerRepository.save(offer).getId();
    }

    public void updateOfferName(Long offerId, String name) {

    }
}
