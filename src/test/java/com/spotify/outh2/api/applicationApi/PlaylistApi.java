package com.spotify.outh2.api.applicationApi;

import com.spotify.outh2.api.RestResource;
import com.spotify.outh2.pojo.Playlist;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static com.spotify.outh2.api.Route.PLAYLISTS;
import static com.spotify.outh2.api.Route.USERS;
import static com.spotify.outh2.api.TokenManager.getToken;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class PlaylistApi {
    @Step
    public static Response post(String user_id, Playlist requestPlaylist) {
        return RestResource.post(getToken(), USERS + "/" + user_id + PLAYLISTS, requestPlaylist);
    }

    @Step
    public static Response post(String invalid_token, String user_id, Playlist requestPlaylist) {
        return RestResource.post(invalid_token, USERS + "/" + user_id + PLAYLISTS, requestPlaylist);
    }

    @Step
    public static Response get(String playlist_id) {
        return RestResource.get(getToken(), PLAYLISTS + "/" + playlist_id);
    }

    @Step
    public static Response put(String playlist_id, Playlist requestPlaylist) {
        return RestResource.put(getToken(), PLAYLISTS + "/" + playlist_id, requestPlaylist);
    }

    @Step
    public static Playlist playListBuilder(String name, String description, Boolean publicValue) {
        return Playlist.builder().
                name(name).
                description(description).
                _public(publicValue).
                build();

    }

    @Step
    public static void assertPlaylistEquals(Playlist responsePlaylist, Playlist requestPlaylist) {
        assertThat(responsePlaylist.getName(), equalTo(requestPlaylist.getName()));
        assertThat(responsePlaylist.getDescription(), equalTo(requestPlaylist.getDescription()));
        assertThat(responsePlaylist.get_public(), equalTo(requestPlaylist.get_public()));
    }
}
