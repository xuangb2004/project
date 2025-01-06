/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.time.LocalDateTime;
import java.util.Date;



/**
 *
 * @author quoct
 */
public class Booking {
    private String bookingId;
    private String customerId;
    private int roomId;
    private LocalDateTime  checkInDate;
    private LocalDateTime  checkOutDate;
    private String numberOfPeople;
    private String requiredSpecial;
    private int invest;
    private double price;
    private String status;

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public Booking(String bookingId, String customerId, int roomId, LocalDateTime checkInDate, LocalDateTime checkOutDate, String numberOfPeople, String requiredSpecial, int invest, double price, String status, String roomRental) {
        this.bookingId = bookingId;
        this.customerId = customerId;
        this.roomId = roomId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.numberOfPeople = numberOfPeople;
        this.requiredSpecial = requiredSpecial;
        this.invest = invest;
        this.price = price;
        this.status = status;
        this.roomRental = roomRental;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public Booking(String bookingId, String customerId, int roomId, LocalDateTime checkInDate, LocalDateTime checkOutDate, String numberOfPeople, String requiredSpecial, int invest, double price, String roomRental) {
        this.bookingId = bookingId;
        this.customerId = customerId;
        this.roomId = roomId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.numberOfPeople = numberOfPeople;
        this.requiredSpecial = requiredSpecial;
        this.invest = invest;
        this.price = price;
        this.roomRental = roomRental;
    }

    public Booking() {
    }
    private String roomRental;

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public void setCheckInDate(LocalDateTime checkInDate) {
        this.checkInDate = checkInDate;
    }

    public void setCheckOutDate(LocalDateTime checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public void setNumberOfPeople(String numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public void setRequiredSpecial(String requiredSpecial) {
        this.requiredSpecial = requiredSpecial;
    }

    public int getInvest() {
        return invest;
    }

    public void setInvest(int invest) {
        this.invest = invest;
    }


    public void setRoomRental(String roomRental) {
        this.roomRental = roomRental;
    }

    public Booking(String bookingId, String customerId, int roomId, LocalDateTime checkInDate, LocalDateTime checkOutDate, String numberOfPeople, String requiredSpecial, int invest, String roomRental,String price) {
        this.bookingId = bookingId;
        this.customerId = customerId;
        this.roomId = roomId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.numberOfPeople = numberOfPeople;
        this.requiredSpecial = requiredSpecial;
        this.invest = invest;
        this.roomRental = roomRental;
        
    }
public Booking(String customerId, int roomId, LocalDateTime checkInDate, LocalDateTime checkOutDate, String numberOfPeople, String requiredSpecial, int invest, String roomRental) {
        this.customerId = customerId;
        this.roomId = roomId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.numberOfPeople = numberOfPeople;
        this.requiredSpecial = requiredSpecial;
        this.invest = invest;
        this.roomRental = roomRental;
    }
   public Booking(int roomId, LocalDateTime checkInDate, LocalDateTime checkOutDate, String numberOfPeople, String requiredSpecial, int invest, String roomRental,double price) {
        this.roomId = roomId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.numberOfPeople = numberOfPeople;
        this.requiredSpecial = requiredSpecial;
        this.invest = invest;
        this.roomRental = roomRental;
        this.price = price;
    }

    public String getBookingId() {
        return bookingId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public int getRoomId() {
        return roomId;
    }

    public LocalDateTime getCheckInDate() {
        return checkInDate;
    }

    public LocalDateTime getCheckOutDate() {
        return checkOutDate;
    }

    public String getNumberOfPeople() {
        return numberOfPeople;
    }

    public String getRequiredSpecial() {
        return requiredSpecial;
    }


    public String getRoomRental() {
        return roomRental;
    }
    
}
