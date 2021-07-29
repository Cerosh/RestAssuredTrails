package com.spotify.outh2.api;

public enum StatusCode {
    CODE_200(200, ""),
    CODE_201(201, ""),
    CODE_401(401, "Invalid access token");
    public int code;
    public String message;

    StatusCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
