package com.spotify.outh2.tests;

import com.spotify.outh2.pojo.ErrorRoot;
import com.spotify.outh2.pojo.Playlist;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.spotify.outh2.api.SpecBuilder.getRequestSpecification;
import static com.spotify.outh2.api.SpecBuilder.getResponseSpecification;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class PlaylistTests {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();
    String user_id = "ovvhvt6g6sf9ci36147nhjnf4";

    @Test
    public void shouldBeAbleToCreatePlaylist(){
        String val = "value";
        Playlist requestPlaylist = new Playlist().
                setName("Playlist name "+ dtf.format(now)).
                setDescription("Playlist description through Pojo with Builder Pattern").
                setPublic(false);
        Playlist responsePlaylist = given(getRequestSpecification()).body(requestPlaylist).
                when().post("/users/"+user_id+"/playlists").
                then().spec(getResponseSpecification()).
                assertThat().statusCode(201).
                contentType(ContentType.JSON).
                extract().as(Playlist.class);
        assertThat(responsePlaylist.getName(), equalTo(requestPlaylist.getName()));
        assertThat(responsePlaylist.getDescription(), equalTo(requestPlaylist.getDescription()));
        assertThat(responsePlaylist.getPublic(), equalTo(requestPlaylist.getPublic()));
    }
    @Test
    public void shouldBeAbleToGetPlaylist(){
        Playlist requestPlaylist = new Playlist().
                setName("Playlist name").
                setDescription("Playlist description").
                setPublic(false);
        Playlist responsePlaylist = given(getRequestSpecification()).
                when().get("/playlists/0MtI3PK5jOMQPNcGYBvLZr").
                then().spec(getResponseSpecification()).
                assertThat().statusCode(200).
                contentType(ContentType.JSON).
                extract().as(Playlist.class);
        assertThat(responsePlaylist.getName(), equalTo(requestPlaylist.getName()));
        assertThat(responsePlaylist.getDescription(), equalTo(requestPlaylist.getDescription()));
        assertThat(responsePlaylist.getPublic(), equalTo(requestPlaylist.getPublic()));
    }
    @Test
    public void shouldBeAbleToUpdatePlaylist(){
        Playlist requestPlaylist = new Playlist().
                setName("Playlist name").
                setDescription("Playlist description through Pojo with builder pattern").
                setPublic(false);

        given(getRequestSpecification()).body(requestPlaylist).
                when().put("/playlists/7hZSEDITUtd56Tdw1OcIpH").
                then().spec(getResponseSpecification()).
                assertThat().statusCode(200);
    }
    @Test
    public void shouldNotBeAbleToCreatePlaylistWithWrongAccessToken(){
        Playlist requestPlaylist = new Playlist().
                setName("Playlist name").
                setDescription("Playlist description through Pojo with builder pattern").
                setPublic(false);

        ErrorRoot responseError = given().baseUri("https://api.spotify.com").
                basePath("/v1").
                header("Authorization","Bearer "+ 123).
                contentType(ContentType.JSON).
                body(requestPlaylist).
                log().all().
                when().post("/users/"+user_id+"/playlists").
                then().spec(getResponseSpecification()).
                assertThat().statusCode(401).
                extract().as(ErrorRoot.class);
        assertThat(responseError.getError().getStatus(), equalTo(401));
        assertThat(responseError.getError().getMessage(), equalTo("Invalid access token"));
    }
}
