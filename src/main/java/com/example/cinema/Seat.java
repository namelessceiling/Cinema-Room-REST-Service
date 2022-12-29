package com.example.cinema;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;

public class Seat {
    private int row;
    private int column;
    private int price;
    @JsonIgnore
    private boolean seatIsAvailable;

    public Seat(int row, int column) {
        this.row = row;
        this.column = column;
        this.seatIsAvailable = true;
        this.price = row <= 4 ? 10 : 8;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getPrice() {
        return price;
    }

    //Used Code->Generate in IntelliJ
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seat seat = (Seat) o;
        return row == seat.row && column == seat.column && price == seat.price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isSeatIsAvailable() {
        return seatIsAvailable;
    }

    public void setSeatIsAvailable(boolean seatIsAvailable) {
        this.seatIsAvailable = seatIsAvailable;
    }
}
