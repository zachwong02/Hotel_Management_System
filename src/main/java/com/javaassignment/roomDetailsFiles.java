//Author: Wong Zhi Zhen
//TP number: TP058282
//Date Created: 14 October 2021

package com.javaassignment;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

public class roomDetailsFiles {

    //For writing files
    private Formatter x;

    //For reading files
    private Scanner y;


    //Function: to open a new file
    public void openFile(){
        try{
            x = new Formatter("roomDetails.txt");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    //Function: to write data into txt file using a standard format

    //Explanation:

    public void addRecords(ArrayList<ArrayList<LocalDate>> dates){ //get multidimensional array
            for(int i =0; i < 20; i++){
                ArrayList<LocalDate> currentRoom = dates.get(i);    //get the every room array inside the multidimensional array
                for(int p = 0; p < currentRoom.size(); p++){
                    x.format("%s ",currentRoom.get(p));             //every date is separated by a space
                }
                x.format("%n");                                     //after each array is written, line break is inserted
            }
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
            y = new Scanner(new File("roomDetails.txt"));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }


    //Function: an algorithm to read the txt file

    //Explanation:

    public ArrayList<ArrayList<LocalDate>> readFile(){                  //accepts multidimensional array

        ArrayList<ArrayList<String>> stringRooms = new ArrayList<>();   //initialize a string multidimensional array

        ArrayList<ArrayList<LocalDate>> rooms = new ArrayList<>();      //initialize final return multidimensional array variable

        ArrayList<String> getRooms = new ArrayList<>();                 //initialize a string array list

        String dates;                                                   //initialize a dates variable to get one whole line data

        if(y.hasNextLine()) {
            while (y.hasNextLine()) {
                for (int i = 0; i < 20; i++) {
                    dates = y.nextLine();                                   //dates from one line will be inserted into dates variable
                    getRooms.add(dates);                                    //it will then be added into the getRooms array to form
                                                                            // a multidimensional array later on
                }
            }
        }


        for(int i = 0; i < 20; i++){                                    //construction of empty final return multidimensional array
            rooms.add(new ArrayList<>());
        }


        if(getRooms.size() > 0) {                                               //if the array has values, it will start converting
                                                                                // strings to LocalDates
            for(int i = 0; i < 20; i++){
                String room = getRooms.get(i);                                  //room variable will get every string from the getRooms array

                String[] sep_date = room.split(" ");                       //the dates will then be separated and be put into an array

                List<String> list_date = Arrays.asList(sep_date);               //the array will then be converted into a List

                ArrayList<String> array_date = new ArrayList<String>(list_date);     //then it will be copied and inserted into an ArrayList

                stringRooms.add(array_date);                                    //all values will then be inserted into a multidimensional
                                                                                // array which contains string values

            }

            for(int i = 0; i < 20; i++){
                if(stringRooms.get(i).size() > 0) {                                     //the algorithm will then parse the strings to be converted
                    for (int j = 0; j < stringRooms.get(i).size(); j++){                //into LocalDate objects
                        try{
                            rooms.get(i).add(LocalDate.parse(stringRooms.get(i).get(j)));
                        }

                        catch(DateTimeParseException e){

                        }

                    }
                }
            }
        }


        return rooms;                                                                      //the final multidimensional array will then be returned
    }

}
