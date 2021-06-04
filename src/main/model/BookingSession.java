package main.model;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

public final class BookingSession {
    public static String bookingSeat;
    public static Date bookingDate;
    public static String bookingOwner;
    public static boolean confirmation;

    public BookingSession(String bookingSeat, Date bookingDate, String bookingOwner, boolean confirmation){
        BookingSession.bookingSeat = bookingSeat;
        BookingSession.bookingDate = bookingDate;
        BookingSession.bookingOwner = bookingOwner;
        BookingSession.confirmation = confirmation;
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

    public static boolean getBookingConfirmation() { return confirmation; }

    public static void deleteBookingObject() {
        BookingSession.bookingSeat = null;
        BookingSession.bookingDate = null;
        BookingSession.bookingOwner = null;
        BookingSession.confirmation = false;
    }

    public static void showData(){
        System.out.println(getBookingSeat()+getBookingOwner()+getBookingDate());
    }
}
