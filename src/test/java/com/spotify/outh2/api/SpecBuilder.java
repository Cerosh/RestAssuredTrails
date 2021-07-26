package com.spotify.outh2.api;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.util.HashMap;

public class SpecBuilder {

    public static RequestSpecification getRequestSpecification(){
        return  new RequestSpecBuilder().
                setBaseUri("https://api.spotify.com").
                setBasePath("/v1").
                setContentType(ContentType.JSON).log(LogDetail.ALL).build();
    }
    public static RequestSpecification getAccountRequestSpecification(HashMap<String,String> formParams){
        return  new RequestSpecBuilder().
                setBaseUri("https://accounts.spotify.com").
                addFormParams(formParams).
                setContentType(ContentType.URLENC).log(LogDetail.ALL).build();
    }

    public static ResponseSpecification getResponseSpecification(){
        return new ResponseSpecBuilder().
                log(LogDetail.ALL).build();
    }
}
