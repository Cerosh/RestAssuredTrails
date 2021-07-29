package com.spotify.outh2.tests;

import com.spotify.outh2.api.RestResource;
import com.spotify.outh2.api.StatusCode;
import com.spotify.outh2.api.applicationApi.PlaylistApi;
import com.spotify.outh2.pojo.ErrorRoot;
import com.spotify.outh2.pojo.Playlist;
import com.spotify.outh2.utils.DataLoader;
import com.spotify.outh2.utils.FakerUtil;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class PlaylistTests extends BaseClass{

    @Test
    public void shouldBeAbleToCreatePlaylist() {
        Playlist requestPlaylist = PlaylistApi.playListBuilder(FakerUtil.generateName(),
                FakerUtil.generateDescription(), false);
        Response response =
                PlaylistApi.post(DataLoader.getInstance().getDataProperty("user_id"), requestPlaylist);
        RestResource.assertStatusCode(response, StatusCode.CODE_201);
        PlaylistApi.assertPlaylistEquals(response.as(Playlist.class), requestPlaylist);
    }

    @Test
    public void shouldBeAbleToGetPlaylist() {
        Playlist requestPlaylist = PlaylistApi.playListBuilder("Playlist name",
                "Playlist description after removing duplicate code", false);
        Response response = PlaylistApi.get(DataLoader.getInstance().getDataProperty("get_playlist_id"));
        RestResource.assertStatusCode(response, StatusCode.CODE_200);
        PlaylistApi.assertPlaylistEquals(response.as(Playlist.class), requestPlaylist);
    }

    @Test
    public void shouldBeAbleToUpdatePlaylist() {
        Playlist requestPlaylist = PlaylistApi.playListBuilder(FakerUtil.generateName(),
                FakerUtil.generateDescription(), false);
        Response response =
                PlaylistApi.put(DataLoader.getInstance().getDataProperty("update_playlist_id"), requestPlaylist);
        RestResource.assertStatusCode(response, StatusCode.CODE_200);
    }

    @Test
    public void shouldNotBeAbleToCreatePlaylistWithWrongAccessToken() {
        Playlist requestPlaylist = PlaylistApi.playListBuilder(FakerUtil.generateName(),
                FakerUtil.generateDescription(), false);
        Response response = PlaylistApi.post("123",
                DataLoader.getInstance().getDataProperty("user_id"), requestPlaylist);
        RestResource.assertStatusCode(response, StatusCode.CODE_401);
        RestResource.assertError(response.as(ErrorRoot.class), StatusCode.CODE_401.message);

    }

}
