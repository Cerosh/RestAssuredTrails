package com.spotify.outh2.tests;

import com.spotify.outh2.pojo.ErrorRoot;
import com.spotify.outh2.pojo.Playlist;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class PlaylistTests {
    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();
    String access_token="BQCTow0ElfY5JCwaRhq5_UzQIFsByH-1aRsqUqG6USxv47Cxkw8_OxEAgMt0g8eaONbseMMCzzdFJJTP_7-BpxRH_97l9iAHYTYBtkL5tJHAWzTkgh9HT7g-bH6wNkQ6rv-HNvU2c1oaH_RzcA0Se-Tjv3CDD88csEb9plG1s0usdmcqT_gYqRrdbwi3-2O1ijYQtwmPKjavD9uBqotYAzJC46Y234sucbPFmdAZ7hgp";
    String user_id = "ovvhvt6g6sf9ci36147nhjnf4";

    @BeforeClass
    public void beforeClass(){
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
                setBaseUri("https://api.spotify.com").
                setBasePath("/v1").
                addHeader("Authorization","Bearer "+ access_token).
                setContentType(ContentType.JSON).log(LogDetail.ALL);
        requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
               log(LogDetail.ALL);
        responseSpecification = responseSpecBuilder.build();
    }
    @Test
    public void shouldBeAbleToCreatePlaylist(){
        String val = "value";
        Playlist requestPlaylist = new Playlist();
        requestPlaylist.setName("Playlist name "+ dtf.format(now));
        requestPlaylist.setDescription("Playlist description through Pojo");
        requestPlaylist.setPublic(false);
        Playlist responsePlaylist = given(requestSpecification).body(requestPlaylist).
                when().post("/users/"+user_id+"/playlists").
                then().spec(responseSpecification).
                assertThat().statusCode(201).
                contentType(ContentType.JSON).
                extract().as(Playlist.class);
        assertThat(responsePlaylist.getName(), equalTo(requestPlaylist.getName()));
        assertThat(responsePlaylist.getDescription(), equalTo(requestPlaylist.getDescription()));
        assertThat(responsePlaylist.getPublic(), equalTo(requestPlaylist.getPublic()));
    }
    @Test
    public void shouldBeAbleToGetPlaylist(){
        Playlist requestPlaylist = new Playlist();
        requestPlaylist.setName("Playlist name");
        requestPlaylist.setDescription("Playlist description");
        requestPlaylist.setPublic(false);
        Playlist responsePlaylist = given(requestSpecification).
                when().get("/playlists/0MtI3PK5jOMQPNcGYBvLZr").
                then().spec(responseSpecification).
                assertThat().statusCode(200).
                contentType(ContentType.JSON).
                extract().as(Playlist.class);
        assertThat(responsePlaylist.getName(), equalTo(requestPlaylist.getName()));
        assertThat(responsePlaylist.getDescription(), equalTo(requestPlaylist.getDescription()));
        assertThat(responsePlaylist.getPublic(), equalTo(requestPlaylist.getPublic()));
    }
    @Test
    public void shouldBeAbleToUpdatePlaylist(){
        Playlist requestPlaylist = new Playlist();
        requestPlaylist.setName("Playlist name");
        requestPlaylist.setDescription("Playlist description through Pojo");
        requestPlaylist.setPublic(false);

        given(requestSpecification).body(requestPlaylist).
                when().put("/playlists/7hZSEDITUtd56Tdw1OcIpH").
                then().spec(responseSpecification).
                assertThat().statusCode(200);
    }
    @Test
    public void shouldNotBeAbleToCreatePlaylistWithWrongAccessToken(){
        Playlist requestPlaylist = new Playlist();
        requestPlaylist.setName("Playlist name");
        requestPlaylist.setDescription("Playlist description through Pojo");
        requestPlaylist.setPublic(false);

        ErrorRoot responseError = given().baseUri("https://api.spotify.com").
                basePath("/v1").
                header("Authorization","Bearer "+ 123).
                contentType(ContentType.JSON).
                body(requestPlaylist).
                log().all().
                when().post("/users/"+user_id+"/playlists").
                then().spec(responseSpecification).
                assertThat().statusCode(401).
                extract().as(ErrorRoot.class);
        assertThat(responseError.getError().getStatus(), equalTo(401));
        assertThat(responseError.getError().getMessage(), equalTo("Invalid access token"));
    }
}
