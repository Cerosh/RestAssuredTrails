package com.spotify.outh2.tests;

import com.spotify.outh2.api.RestResource;
import com.spotify.outh2.api.applicationApi.PlaylistApi;
import com.spotify.outh2.pojo.ErrorRoot;
import com.spotify.outh2.pojo.Playlist;
import com.spotify.outh2.utils.DataLoader;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PlaylistTests {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();

    @Test
    public void shouldBeAbleToCreatePlaylist() {
        Playlist requestPlaylist = PlaylistApi.playListBuilder("Playlist name"+ dtf.format(now) ,
                "Playlist description after removing duplicate code", false);
        Response response =
                PlaylistApi.post(DataLoader.getInstance().getDataProperty("user_id"), requestPlaylist);
        RestResource.assertStatusCode(response, 201);
        PlaylistApi.assertPlaylistEquals(response.as(Playlist.class), requestPlaylist);
    }

    @Test
    public void shouldBeAbleToGetPlaylist() {
        Playlist requestPlaylist = PlaylistApi.playListBuilder("Playlist name",
                "Playlist description after removing duplicate code", false);
        Response response = PlaylistApi.get(DataLoader.getInstance().getDataProperty("get_playlist_id"));
        RestResource.assertStatusCode(response, 200);
        PlaylistApi.assertPlaylistEquals(response.as(Playlist.class), requestPlaylist);
    }

    @Test
    public void shouldBeAbleToUpdatePlaylist() {
        Playlist requestPlaylist = PlaylistApi.playListBuilder("Playlist name",
                "Playlist description through Pojo with builder pattern", false);
        Response response =
                PlaylistApi.put(DataLoader.getInstance().getDataProperty("update_playlist_id"), requestPlaylist);
        RestResource.assertStatusCode(response, 200);
    }

    @Test
    public void shouldNotBeAbleToCreatePlaylistWithWrongAccessToken() {
        Playlist requestPlaylist = PlaylistApi.playListBuilder("Playlist name",
                "Playlist description through Pojo with builder pattern", false);
        Response response = PlaylistApi.post("123",
                DataLoader.getInstance().getDataProperty("user_id"), requestPlaylist);
        RestResource.assertStatusCode(response, 401);
        RestResource.assertError(response.as(ErrorRoot.class), "Invalid access token");

    }

}
