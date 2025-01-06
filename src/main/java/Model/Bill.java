/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author quoct
 */
public class Bill {
    private int invoiceId;
    private int bookingId;
    private double totalInvoicePrice;
    private  String EmployeeID;

    public int getInvoiceId() {
        return invoiceId;
    }

    public int getBookingId() {
        return bookingId;
    }

    public Bill(int invoiceId, int bookingId, double totalInvoicePrice, String EmployeeID) {
        this.invoiceId = invoiceId;
        this.bookingId = bookingId;
        this.totalInvoicePrice = totalInvoicePrice;
        this.EmployeeID = EmployeeID;
    }


    public double getTotalInvoicePrice() {
        return totalInvoicePrice;
    }

    public String getEmployeeID() {
        return EmployeeID;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }



    public void setTotalInvoicePrice(double totalInvoicePrice) {
        this.totalInvoicePrice = totalInvoicePrice;
    }

    public void setEmployeeID(String EmployeeID) {
        this.EmployeeID = EmployeeID;
    }
    
}
