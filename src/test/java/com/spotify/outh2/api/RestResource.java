package com.spotify.outh2.api;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.HashMap;

import static com.spotify.outh2.api.SpecBuilder.*;
import static io.restassured.RestAssured.given;

public class RestResource {
    public static Response post(String access_token, String path, Object request_body){
        return given(getRequestSpecification()).
                header("Authorization","Bearer "+ access_token).body(request_body).
                when().post(path).
                then().spec(getResponseSpecification()).extract().response();
    }
    public static Response postAccount(HashMap<String,String> formParams){
        return given(getAccountRequestSpecification(formParams)).
                when().post("/api/token").
                then().spec(getResponseSpecification()).
                extract().response();
    }

    public static Response get(String access_token, String path){
        return given(getRequestSpecification()).header("Authorization","Bearer "+ access_token).
                when().get(path).
                then().spec(getResponseSpecification()).extract().response();
    }

    public static Response put (String access_token, String path, Object request_body){
        return given(getRequestSpecification()).
                header("Authorization","Bearer "+ access_token).body(request_body).
                when().put(path).
                then().spec(getResponseSpecification()).extract().response();
    }
}
