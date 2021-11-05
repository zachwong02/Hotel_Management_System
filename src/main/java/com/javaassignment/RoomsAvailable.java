
//Author: Wong Zhi Zhen
//TP number: TP058282
//Date Created: 14 October 2021

package com.javaassignment;

import java.time.LocalDate;

public class RoomsAvailable {
    private int roomNo;
    private String available;
    private LocalDate stayDate;

    public RoomsAvailable(int roomNo) {
        this.roomNo = roomNo;
        this.available = "Available";
    }

    public RoomsAvailable(int roomNo, LocalDate stayDate) {
        this.roomNo = roomNo;
        this.available = "Not Available";
    }

    public int getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(int roomNo) {
        this.roomNo = roomNo;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public LocalDate getStayDate() {
        return stayDate;
    }

    public void setStayDate(LocalDate stayDate) {
        this.stayDate = stayDate;
    }
}
