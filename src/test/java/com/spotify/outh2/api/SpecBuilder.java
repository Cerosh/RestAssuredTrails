package com.spotify.outh2.api;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class SpecBuilder {
    static String access_token="BQCqAtSYDisjPrFKi7yoLVJ2aEsZA6OqVpMKMQnwjv8cbtCmFwMiuUM_JZTBOLh0toNyPzusz3vWqant0UBc2kFKup2KOx69PbJoaFb_HNcLbs-nJWQY6J3E1XQefodlw7TjnyTqVANRtq4utPiav8TdAtd3YnlNrWi6BFEOZfjbFTelfCGkLoaqvwaIZOt0aBx_8Wb73PF6hBRlFmYzPd01S8VLmFnafUv_0ka86Lb8s6M72FxXnC4ilQ";
    public static RequestSpecification getRequestSpecification(){
        return  new RequestSpecBuilder().
                setBaseUri("https://api.spotify.com").
                setBasePath("/v1").
                addHeader("Authorization","Bearer "+ access_token).
                setContentType(ContentType.JSON).log(LogDetail.ALL).build();
    }

    public static ResponseSpecification getResponseSpecification(){
        return new ResponseSpecBuilder().
                log(LogDetail.ALL).build();
    }
}
