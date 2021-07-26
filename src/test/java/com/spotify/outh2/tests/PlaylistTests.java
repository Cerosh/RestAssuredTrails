package com.spotify.outh2.tests;

import com.spotify.outh2.api.applicationApi.PlaylistApi;
import com.spotify.outh2.pojo.ErrorRoot;
import com.spotify.outh2.pojo.Playlist;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class PlaylistTests {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();
    String user_id = "ovvhvt6g6sf9ci36147nhjnf4";

    @Test
    public void shouldBeAbleToCreatePlaylist(){
        Playlist requestPlaylist = new Playlist().
                setName("Playlist name "+ dtf.format(now)).
                setDescription("Playlist description through Pojo with Builder Pattern").
                setPublic(false);

        Response response = PlaylistApi.post(user_id, requestPlaylist);
        assertThat(response.statusCode(),equalTo(201));
//        assertThat(response.contentType(),equalTo(ContentType.JSON));
        Playlist responsePlaylist = response.as(Playlist.class);

        assertThat(responsePlaylist.getName(), equalTo(requestPlaylist.getName()));
        assertThat(responsePlaylist.getDescription(), equalTo(requestPlaylist.getDescription()));
        assertThat(responsePlaylist.getPublic(), equalTo(requestPlaylist.getPublic()));
    }
    @Test
    public void shouldBeAbleToGetPlaylist(){
        Playlist requestPlaylist = new Playlist().
                setName("Playlist name").
                setDescription("Playlist description through Pojo with builder pattern").
                setPublic(false);

        Response response = PlaylistApi.get("7hZSEDITUtd56Tdw1OcIpH");
        assertThat(response.statusCode(), equalTo(200));
//        assertThat(response.contentType(),equalTo(ContentType.JSON));
        Playlist responsePlaylist = response.as(Playlist.class);

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

        Response response = PlaylistApi.put("7hZSEDITUtd56Tdw1OcIpH", requestPlaylist);
        assertThat(response.statusCode(),equalTo(200));
    }
    @Test
    public void shouldNotBeAbleToCreatePlaylistWithWrongAccessToken(){
        Playlist requestPlaylist = new Playlist().
                setName("Playlist name").
                setDescription("Playlist description through Pojo with builder pattern").
                setPublic(false);

        Response response = PlaylistApi.post("123",user_id,requestPlaylist);
        assertThat(response.statusCode(), equalTo(401));
        ErrorRoot responseError = response.as(ErrorRoot.class);
        assertThat(responseError.getError().getStatus(), equalTo(401));
        assertThat(responseError.getError().getMessage(), equalTo("Invalid access token"));
    }
}
