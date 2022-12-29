package com.example.cinema.Model;

import com.example.cinema.Seat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cinema {
    @JsonProperty("total_rows")
    private int totalRows;
    @JsonProperty("total_columns")
    private int totalColumns;
    @JsonProperty("available_seats")
    private List<Seat> seats;
    @JsonIgnore
    private Map<String, Seat> purchasedSeats;

    public Cinema(int totalRows, int totalColumns) {
        this.totalRows = totalRows;
        this.totalColumns = totalColumns;
        this.purchasedSeats = new ConcurrentHashMap<>();
        this.seats = new ArrayList<>();
        for (int i = 1; i <= totalRows; i++) {
            for (int j = 1; j <= totalColumns; j++) {
                this.seats.add(new Seat(i, j));
            }
        }
    }

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    public int getTotalColumns() {
        return totalColumns;
    }

    public void setTotalColumns(int totalColumns) {
        this.totalColumns = totalColumns;
    }

    public Map<String, Seat> getPurchasedSeats() {
        return purchasedSeats;
    }

    public void setPurchasedSeats(Map<String, Seat> purchasedSeats) {
        this.purchasedSeats = purchasedSeats;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }
}
