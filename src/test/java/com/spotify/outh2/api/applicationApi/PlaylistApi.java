package com.spotify.outh2.api.applicationApi;

import com.spotify.outh2.api.RestResource;
import com.spotify.outh2.pojo.Playlist;
import io.restassured.response.Response;

public class PlaylistApi {
    static String access_token = "BQCPlAafMMXY4n3M1zT1Gng0hRKpwNib1lDw46pz_6zVWbG4oyWKNFQJ9YFZl7IwlZjneogtq14R4ff7YsY8aZ9Mqs2YQa-jeJ-wfOVGI4_eZtaGc1UdPhaCuEwbt3-gBl2iPZPtyODE0ea6tKPIkrPZ8X5X3DNOPFQqyQ7wkxKpwnvO9dxa1NPaac7EiVyYkLahn_qigtbLJoF-sMn3WeSEg6EY1dHbNpDUCQStew1xByzXHYFjagdzoA";

    public static Response post(String user_id, Playlist requestPlaylist) {
        return RestResource.post(access_token, "/users/" + user_id + "/playlists", requestPlaylist);
    }

    public static Response post(String invalid_token, String user_id, Playlist requestPlaylist) {
        return RestResource.post(invalid_token, "/users/" + user_id + "/playlists", requestPlaylist);
    }

    public static Response get(String playlist_id) {
        return RestResource.get(access_token, "/playlists/" + playlist_id);
    }

    public static Response put(String playlist_id, Playlist requestPlaylist) {
        return RestResource.put(access_token, "/playlists/" + playlist_id, requestPlaylist);
    }
}
