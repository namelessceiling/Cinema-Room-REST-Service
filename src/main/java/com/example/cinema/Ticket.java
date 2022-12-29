package com.example.cinema;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class Ticket {
    @JsonProperty("ticket")
    private Seat seat;
    private String token;

    public Ticket(Seat seat) {
        this.seat = seat;
        this.token = UUID.randomUUID().toString();
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
