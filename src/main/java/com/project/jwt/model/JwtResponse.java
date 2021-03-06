package com.project.jwt.model;

public class JwtResponse {

    private final String jwttoken;

    public JwtResponse(String jwttoken) {
        this.jwttoken = jwttoken;
    }

    //"token" : "..."
    public String getToken() {
        return this.jwttoken;
    }
}
