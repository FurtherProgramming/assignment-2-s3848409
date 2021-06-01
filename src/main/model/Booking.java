package main.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class Booking {
    public static String bookingSeat;
    public static LocalDate bookingDate;
    public static String bookingOwner;

    public Booking(String bookingSeat, LocalDate bookingDate, String bookingOwner){
        Booking.bookingSeat = bookingSeat;
        Booking.bookingDate = bookingDate;
        Booking.bookingOwner = bookingOwner;
    }

    public static String getBookingSeat() {
        return bookingSeat;
    }

    public static LocalDate getBookingDate() {
        return bookingDate;
    }

    public static String getBookingDateAsString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
        return bookingDate.format(formatter);
    }

    public static String getBookingOwner() {
        return bookingOwner;
    }

    public static void deleteBookingObject() {
        Booking.bookingSeat = null;
        Booking.bookingDate = null;
        Booking.bookingOwner = null;
    }
}
