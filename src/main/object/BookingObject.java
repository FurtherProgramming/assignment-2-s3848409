package main.object;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BookingObject {
    SimpleDateFormat newFormat = new SimpleDateFormat("dd MMM yyyy");
    String bookingSeat;
    String bookingDate;
    String bookingOwner;
    boolean bookingStatus;
    Date tempDate;

    public BookingObject(String bookingSeat, String bookingDate, String bookingOwner, boolean bookingStatus) throws ParseException {
        this.bookingSeat = bookingSeat;
        this.bookingOwner = bookingOwner;
        tempDate = new SimpleDateFormat("yyyy-MM-dd").parse(bookingDate);
        this.bookingDate = newFormat.format(tempDate);
        this.bookingStatus = bookingStatus;
    }

    public String getBookingSeat() {
        return bookingSeat;
    }

    public Date getBookingDateAsDate() {
        return tempDate;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public String getBookingOwner() {
        return bookingOwner;
    }

    public String getBookingStatus() {
        String bookingStatusAsString;
        if(bookingStatus){
            bookingStatusAsString = "Approved";
        }else{
            bookingStatusAsString = "Not Approved";
        }
        return bookingStatusAsString;
    }

    public boolean getBookingStatusAsBool() { return bookingStatus; }

    public void deleteBookingObject() {
        this.bookingSeat = null;
        this.bookingDate = null;
        this.bookingOwner = null;
        this.bookingStatus = false;
    }

}
