package main.session;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public final class BookingSession {
    public static String bookingSeat;
    public static Date bookingDate;
    public static String bookingOwner;
    public static boolean bookingStatus;

    public BookingSession(String bookingSeat, Date bookingDate, String bookingOwner, boolean bookingStatus){
        BookingSession.bookingSeat = bookingSeat;
        BookingSession.bookingDate = bookingDate;
        BookingSession.bookingOwner = bookingOwner;
        BookingSession.bookingStatus = bookingStatus;
    }

    public static String getBookingSeat() {
        return bookingSeat;
    }

    public static Date getBookingDate() {
        return bookingDate;
    }

    public static String getBookingDateAsString() {
        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        return dateFormat.format(bookingDate);
    }

    public static String getBookingOwner() {
        return bookingOwner;
    }

    public static boolean getBookingStatus() {
        return bookingStatus;
    }

    public static String getBookingStatusAsString() {
        String status;
        if(bookingStatus){
            status = "Approved";
        }else{
            status = "Not Approved";
        }
        return status;
    }

    public static void deleteBookingObject() {
        BookingSession.bookingSeat = null;
        BookingSession.bookingDate = null;
        BookingSession.bookingOwner = null;
        BookingSession.bookingStatus = false;
    }

}
