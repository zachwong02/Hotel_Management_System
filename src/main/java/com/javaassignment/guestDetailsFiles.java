//Author: Wong Zhi Zhen
//TP number: TP058282
//Date Created: 14 October 2021

package com.javaassignment;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;

public class guestDetailsFiles {

    //For writing files
    private Formatter x;

    //For reading files
    private Scanner y;

    //Function: to open a new file
    public void openFile(){
        try{
            x = new Formatter("guestDetails.txt");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    //Function: to write data into txt file using a standard format
    public void addRecords(String guestFirstName, String guestLastName, String guestEmail, long guestIC, int guestContact, int guestRoom, int guestStayDays, LocalDate guestStartDate){
        x.format("%s %s %s %s %s %s %s %s%n",guestFirstName,guestLastName,guestEmail,guestIC,guestContact,guestRoom,guestStayDays,guestStartDate);


    }

    //Function: to close writing to file
    public void closeFormatter(){
        x.close();
    }


    //Function: to close reading from file
    public void closeScanner(){
        y.close();
    }


    //Function: to open from an existing file to read from
    public void openExistingFile(){

        try{
            y = new Scanner(new File("guestDetails.txt"));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }


    //Function: an algorithm to read the txt file
    public ArrayList<Guest> readFile(){

        //initialization of variables

        ArrayList<Guest> guestList = new ArrayList<>();
        String guestFirstName = "";
        String guestLastName = "";
        String guestEmail = "";
        String guestIC = "";
        String guestContact = "";
        String guestRoom = "";
        String guestStayDays = "";
        String guestStartDate = "";

        //Explaination:

        //While the line has another word beside it
        while (y.hasNext()) {
            guestFirstName = y.next(); //The characters will be inserted into the variables
            guestLastName = y.next();
            guestEmail = y.next();
            guestIC = y.next();
            guestContact = y.next();
            guestRoom = y.next();
            guestStayDays = y.next();
            guestStartDate = y.next();

            //The variables will be inseted into an array to create guest objects

            guestList.add(new Guest(guestFirstName,guestLastName,guestEmail,Long.parseLong(guestIC),Integer.parseInt(guestContact),Integer.parseInt(guestRoom),Integer.parseInt(guestStayDays),LocalDate.parse(guestStartDate)));

            //The system will continue to read if it has a next line
            if (y.hasNextLine()){y.nextLine();}
        }

        //The function will then return the array
        return guestList;
    }




}


