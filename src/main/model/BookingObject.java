package main.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BookingObject {
    SimpleDateFormat newFormat = new SimpleDateFormat("dd MMM yyyy");
    String bookingSeat;
    String bookingDate;
    String bookingOwner;
    String bookingStatus;

    public BookingObject(String bookingSeat, String bookingDate, String bookingOwner, String bookingStatus) throws ParseException {
        this.bookingSeat = bookingSeat;
        this.bookingOwner = bookingOwner;
        Date tempDate = new SimpleDateFormat("yyyy-MM-dd").parse(bookingDate);
        this.bookingDate = newFormat.format(tempDate);
        if(bookingStatus.equals("1") || bookingStatus.equals("true")){
            this.bookingStatus = "Approved";
        }else{
            this.bookingStatus = "Not Approved";
        }
    }

    public String getBookingSeat() {
        return bookingSeat;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public String getBookingOwner() {
        return bookingOwner;
    }

    public String getBookingStatus() { return bookingStatus; }

    public void deleteBookingObject() {
        this.bookingSeat = null;
        this.bookingDate = null;
        this.bookingOwner = null;
        this.bookingStatus = null;
    }

}
