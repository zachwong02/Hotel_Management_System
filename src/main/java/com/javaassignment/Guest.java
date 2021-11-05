//Author: Wong Zhi Zhen
//TP number: TP058282
//Date Created: 14 October 2021

package com.javaassignment;

import java.time.LocalDate;

public class Guest {
    private String guestFirstName, guestLastName, guestEmail;
    private int guestContact, guestRoom, guestStayDays;
    private long guestIC;
    private LocalDate guestStartDate;
    final private int touristTax = 10, roomCharges= 350;
    final private double serviceTax = 0.1;

    public Guest(String guestFirstName,String guestLastName, String guestEmail, long guestIC, int guestContact, int guestRoom, int guestStayDays, LocalDate guestStartDate) {
        this.guestFirstName = guestFirstName;
        this.guestLastName = guestLastName;
        this.guestEmail = guestEmail;
        this.guestIC = guestIC;
        this.guestContact = guestContact;
        this.guestRoom = guestRoom;
        this.guestStayDays = guestStayDays;
        this.guestStartDate = guestStartDate;
    }

    public Guest(String guestFirstName, String guestLastName, int guestStayDays, int guestContact, int guestRoom) {
        this.guestFirstName = guestFirstName;
        this.guestLastName = guestLastName;
        this.guestContact = guestContact;
        this.guestRoom = guestRoom;
        this.guestStayDays = guestStayDays;
    }

    public double getSubtotal(){
        return this.roomCharges * this.guestStayDays;
    }

    public double getTotalOfServiceTax(){
        return getSubtotal()*this.serviceTax;
    }

    public double getTotalofTourismTax(){
        return this.guestStayDays * this.touristTax;
    }

    public double totalCharges(){
        return getSubtotal() + getTotalOfServiceTax() + getTotalofTourismTax();
    }

    public String getGuestFirstName() {
        return guestFirstName;
    }

    public void setGuestFirstName(String guestFirstName) {
        this.guestFirstName = guestFirstName;
    }

    public String getGuestLastName() {
        return guestLastName;
    }

    public void setGuestLastName(String guestLastName) {
        this.guestLastName = guestLastName;
    }

    public String getGuestEmail() {
        return guestEmail;
    }

    public void setGuestEmail(String guestEmail) {
        this.guestEmail = guestEmail;
    }

    public int getGuestContact() {
        return guestContact;
    }

    public void setGuestContact(int guestContact) {
        this.guestContact = guestContact;
    }

    public int getGuestRoom() {
        return guestRoom;
    }

    public void setGuestRoom(int guestRoom) {
        this.guestRoom = guestRoom;
    }

    public int getGuestStayDays() {
        return guestStayDays;
    }

    public void setGuestStayDays(int guestStayDays) {
        this.guestStayDays = guestStayDays;
    }

    public long getGuestIC() {
        return guestIC;
    }

    public void setGuestIC(long guestIC) {
        this.guestIC = guestIC;
    }

    public LocalDate getGuestStartDate() {
        return guestStartDate;
    }

    public void setGuestStartDate(LocalDate guestStartDate) {
        this.guestStartDate = guestStartDate;
    }

    public int getTouristTax() {
        return touristTax;
    }

    public int getRoomCharges() {
        return roomCharges;
    }

    public double getServiceTax() {
        return serviceTax;
    }
}
