package com.spotify.outh2.api.applicationApi;

import com.spotify.outh2.api.RestResource;
import com.spotify.outh2.pojo.Playlist;
import io.restassured.response.Response;

import static com.spotify.outh2.api.TokenManager.getToken;
import static com.spotify.outh2.api.TokenManager.renewToken;

public class PlaylistApi {

    public static Response post(String user_id, Playlist requestPlaylist) {
        return RestResource.post(getToken(), "/users/" + user_id + "/playlists", requestPlaylist);
    }

    public static Response post(String invalid_token, String user_id, Playlist requestPlaylist) {
        return RestResource.post(invalid_token, "/users/" + user_id + "/playlists", requestPlaylist);
    }

    public static Response get(String playlist_id) {
        return RestResource.get(getToken(), "/playlists/" + playlist_id);
    }

    public static Response put(String playlist_id, Playlist requestPlaylist) {
        return RestResource.put(getToken(), "/playlists/" + playlist_id, requestPlaylist);
    }
}
