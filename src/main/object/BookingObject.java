package main.object;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;

public class BookingObject {
    SimpleDateFormat newFormat = new SimpleDateFormat("dd MMM yyyy");
    String bookingSeat;
    Date bookingDate;
    String bookingOwner;
    boolean bookingStatus;
    boolean checkIn;
    boolean covidLocked;

    public BookingObject(String bookingSeat, String bookingDate, String bookingOwner, boolean bookingStatus, boolean checkIn, boolean covidLocked) throws ParseException {
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

    public boolean getBookingCheckIn() { return checkIn; }

    public boolean getCovidLocked() { return covidLocked; }

    public String getBookingStatus() {
        String bookingStatusAsString;
        if(bookingStatus){
            bookingStatusAsString = "Approved";
        }else{
            bookingStatusAsString = "Not Approved";
        }
        return bookingStatusAsString;
    }

    public void deleteBookingObject() {
        this.bookingSeat = null;
        this.bookingDate = null;
        this.bookingOwner = null;
        this.bookingStatus = false;
    }

}
