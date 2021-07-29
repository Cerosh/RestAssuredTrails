package com.spotify.outh2.api;

import com.spotify.outh2.utils.ConfigLoader;
import io.restassured.response.Response;

import java.time.Instant;
import java.util.HashMap;


public class TokenManager {
    private static String access_token;
    private static Instant expiry_time;

    public synchronized static String getToken() {
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
            e.printStackTrace();
            throw new RuntimeException("Fetching Token failed");
        }
        return access_token;
    }

    public static Response renewToken() {
        HashMap<String, String> formParams = new HashMap<>();
        formParams.put("client_id", ConfigLoader.getInstance().getConfigProperty("client_id"));
        formParams.put("client_secret", ConfigLoader.getInstance().getConfigProperty("client_secret"));
        formParams.put("grant_type", ConfigLoader.getInstance().getConfigProperty("grant_type"));
        formParams.put("refresh_token", ConfigLoader.getInstance().getConfigProperty("refresh_token"));
        Response response = RestResource.postAccount(formParams);
        if (response.statusCode() != 200) {
            throw new RuntimeException("Token renewal failed");
        }
        return response;
    }
}
