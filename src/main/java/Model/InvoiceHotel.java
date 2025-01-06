/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author quoct
 */
public class InvoiceHotel {
    private int invoiceID;
    private int bookingId;
    private int serviceInvoiceId;
    private double totalPrice;

    public InvoiceHotel(int invoiceID, int bookingId, int serviceInvoiceId, double totalPrice) {
        this.invoiceID = invoiceID;
        this.bookingId = bookingId;
        this.serviceInvoiceId = serviceInvoiceId;
        this.totalPrice = totalPrice;
    }

    public void setInvoiceID(int invoiceID) {
        this.invoiceID = invoiceID;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public void setServiceInvoiceId(int serviceInvoiceId) {
        this.serviceInvoiceId = serviceInvoiceId;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getInvoiceID() {
        return invoiceID;
    }

    public int getBookingId() {
        return bookingId;
    }

    public int getServiceInvoiceId() {
        return serviceInvoiceId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
    
}
