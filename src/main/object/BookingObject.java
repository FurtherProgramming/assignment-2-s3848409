package main.object;

import java.text.ParseException;
import java.sql.Date;

public class BookingObject {
    String bookingSeat;
    Date bookingDate;
    String bookingOwner;
    boolean bookingStatus;
    boolean checkIn;
    String covidLocked;

    public BookingObject(String bookingSeat, String bookingDate, String bookingOwner, boolean bookingStatus, boolean checkIn, String covidLocked) throws ParseException {
        this.bookingSeat = bookingSeat;
        this.bookingOwner = bookingOwner;
        this.bookingDate = Date.valueOf(bookingDate);
        this.bookingStatus = bookingStatus;
        this.checkIn = checkIn;
        this.covidLocked = covidLocked;
    }

    public String getBookingSeat() {
        return bookingSeat;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public String getBookingOwner() {
        return bookingOwner;
    }

    public boolean getBookingStatusAsBool() { return bookingStatus; }

    public String getCheckIn() {
        if(checkIn){
            return "Yes";
        }else{
            return "No";
        }
    }

    public boolean getBookingCheckInAsBool(){
        return checkIn;
    }

    public String getCovidLocked() { return covidLocked; }

    public String getBookingStatus() {
        if(bookingStatus){
            return "Approved";
        }else{
            return "Not Approved";
        }
    }

}
