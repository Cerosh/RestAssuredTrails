package com.spotify.outh2.api;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.time.Instant;
import java.util.HashMap;

import static com.spotify.outh2.api.RestResource.postAccount;
import static com.spotify.outh2.api.SpecBuilder.getResponseSpecification;
import static io.restassured.RestAssured.given;


public class TokenManager {
    private static String access_token;
    private static Instant expiry_time;

    public static String getToken() {
        try {
            if ((access_token == null) || (Instant.now().isAfter(expiry_time))) {
                System.out.println("Renewing token");
                Response response = renewToken();
                access_token = response.path("access_token");
                int expiryDurationInSeconds = response.path("expires_in");
                expiry_time = Instant.now().plusSeconds(expiryDurationInSeconds - 300);
            } else {
                System.out.println("Token is valid");
            }
        } catch (Exception e) {
            throw new RuntimeException("Fetching Token failed");
        }
        return access_token;
    }

    public static Response renewToken() {
        HashMap<String, String> formParams = new HashMap<>();
        formParams.put("client_id", "cb60b3c0668d4992a812ee1360ff3820");
        formParams.put("client_secret", "d25962af4b43435684d168dcba27c73d");
        formParams.put("grant_type", "refresh_token");
        formParams.put("refresh_token", "AQBQtk6PWz0EUnvFAX0Fdvw7Pq5tOKRbIK_uQ5zds-f-CPgl1F8hHD9myTPQi5tmI4gVrtzje6hgNKxy9AnEuNTM0lWVYTExlyAFSG_rks0FE7OFKTGRnejFQrAlZtaUSuU");
        Response response = RestResource.postAccount(formParams);
        if (response.statusCode() != 200) {
            throw new RuntimeException("Token renewal failed");
        }
        return response;
    }
}
