package com.fdmgroup.management.AccountCustMgmt.services;

import com.fdmgroup.management.AccountCustMgmt.model.Geolocation;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class GeolocationClient implements IGeolocationClient {
    WebClient client;

    public GeolocationClient(WebClient client) {
        super();
        this.client = client;
    }

    @Override
    public Geolocation getLocation(String postalCode) {
        String normalized = postalCode.replaceAll("\\s+", "").toUpperCase();

        var json = client.get()
                .uri(uri -> uri.path("/")
                        .queryParam("locate", normalized)
                        .queryParam("json", 1)
                        .build())
                .retrieve()
                .onStatus(HttpStatusCode::isError, resp ->
                        resp.bodyToMono(String.class)
                                .map(msg -> new RuntimeException("Geocoder error " + resp.statusCode() + ": " + msg)))
                .bodyToMono(com.fasterxml.jackson.databind.JsonNode.class)
                .block();

        if (json == null) {
            throw new IllegalStateException("Empty geocoder response");
        }

        // Prefer nested fields under "standard", fall back to root if provider changes
        var std = json.path("standard");
        String city = nonBlank(std.path("city").asText(null), json.path("city").asText(null));
        String prov = nonBlank(std.path("prov").asText(null), json.path("prov").asText(null));

        if (isBlank(city) || isBlank(prov)) {
            throw new IllegalStateException("Missing city/prov in geocoder response: " + json);
        }
        return new Geolocation(city.trim(), prov.trim());
    }
    private static boolean isBlank(String s) { return s == null || s.isBlank(); }
    private static String nonBlank(String a, String b) { return !isBlank(a) ? a : b; }
}
