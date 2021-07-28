package com.spotify.outh2.api;

import com.spotify.outh2.pojo.ErrorRoot;
import io.restassured.response.Response;

import java.util.HashMap;

import static com.spotify.outh2.api.Route.API;
import static com.spotify.outh2.api.Route.TOKEN;
import static com.spotify.outh2.api.SpecBuilder.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class RestResource {
    public static Response post(String access_token, String path, Object request_body) {
        return given(getRequestSpecification()).
                auth().oauth2(access_token).
                body(request_body).
                when().post(path).
                then().spec(getResponseSpecification()).extract().response();
    }

    public static Response postAccount(HashMap<String, String> formParams) {
        return given(getAccountRequestSpecification(formParams)).
                when().post(API + TOKEN).
                then().spec(getResponseSpecification()).
                extract().response();
    }

    public static Response get(String access_token, String path) {
        return given(getRequestSpecification()).auth().oauth2(access_token).
                when().get(path).
                then().spec(getResponseSpecification()).extract().response();
    }

    public static Response put(String access_token, String path, Object request_body) {
        return given(getRequestSpecification()).
                auth().oauth2(access_token).body(request_body).
                when().put(path).
                then().spec(getResponseSpecification()).extract().response();
    }

    public static void assertStatusCode(Response response, StatusCode statusCode) {
        assertThat(response.statusCode(), equalTo(statusCode.code));
    }

    public static void assertError(ErrorRoot responseError, String errorMessage) {
        assertThat(responseError.getError().getMessage(), equalTo(errorMessage));
    }
}
