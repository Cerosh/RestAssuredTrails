package com.spotify.outh2.tests;

import com.spotify.outh2.api.applicationApi.PlaylistApi;
import com.spotify.outh2.pojo.ErrorRoot;
import com.spotify.outh2.pojo.Playlist;
import com.spotify.outh2.utils.DataLoader;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class PlaylistTests {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();

    @Test
    public void shouldBeAbleToCreatePlaylist() {
        Playlist requestPlaylist = new Playlist().
                setName("Playlist name " + dtf.format(now)).
                setDescription("Playlist description through Pojo with Builder Pattern").
                setPublic(false);

        Response response =
                PlaylistApi.post(DataLoader.getInstance().getDataProperty("user_id"), requestPlaylist);
        assertThat(response.statusCode(), equalTo(201));
        Playlist responsePlaylist = response.as(Playlist.class);

        assertThat(responsePlaylist.getName(), equalTo(requestPlaylist.getName()));
        assertThat(responsePlaylist.getDescription(), equalTo(requestPlaylist.getDescription()));
        assertThat(responsePlaylist.getPublic(), equalTo(requestPlaylist.getPublic()));
    }

    @Test
    public void shouldBeAbleToGetPlaylist() {
        Playlist requestPlaylist = new Playlist().
                setName("Playlist name").
                setDescription("Playlist description through Pojo with builder pattern").
                setPublic(false);

        Response response = PlaylistApi.get(DataLoader.getInstance().getDataProperty("get_playlist_id"));
        assertThat(response.statusCode(), equalTo(200));
        Playlist responsePlaylist = response.as(Playlist.class);

        assertThat(responsePlaylist.getName(), equalTo(requestPlaylist.getName()));
        assertThat(responsePlaylist.getDescription(), equalTo(requestPlaylist.getDescription()));
        assertThat(responsePlaylist.getPublic(), equalTo(requestPlaylist.getPublic()));
    }

    @Test
    public void shouldBeAbleToUpdatePlaylist() {
        Playlist requestPlaylist = new Playlist().
                setName("Playlist name").
                setDescription("Playlist description through Pojo with builder pattern").
                setPublic(false);

        Response response =
                PlaylistApi.put(DataLoader.getInstance().getDataProperty("update_playlist_id"), requestPlaylist);
        assertThat(response.statusCode(), equalTo(200));
    }

    @Test
    public void shouldNotBeAbleToCreatePlaylistWithWrongAccessToken() {
        Playlist requestPlaylist = new Playlist().
                setName("Playlist name").
                setDescription("Playlist description through Pojo with builder pattern").
                setPublic(false);

        Response response = PlaylistApi.post("123", DataLoader.getInstance().getDataProperty("user_id"), requestPlaylist);
        assertThat(response.statusCode(), equalTo(401));
        ErrorRoot responseError = response.as(ErrorRoot.class);
        assertThat(responseError.getError().getStatus(), equalTo(401));
        assertThat(responseError.getError().getMessage(), equalTo("Invalid access token"));
    }
}
