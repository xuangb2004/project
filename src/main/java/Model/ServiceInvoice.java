/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author quoct
 */
public class ServiceInvoice {
    private int serviceInvoiceId;
    private String serviceID;
    private int roomID;
    private int quantity;
    private double totalPrice;
    private String status;

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public ServiceInvoice(int serviceInvoiceId, String serviceID, int roomID, int quantity, double totalPrice, String status) {
        this.serviceInvoiceId = serviceInvoiceId;
        this.serviceID = serviceID;
        this.roomID = roomID;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.status = status;
    }
    public ServiceInvoice(int serviceInvoiceId, String serviceID, int roomID, int quantity, double totalPrice) {
        this.serviceInvoiceId = serviceInvoiceId;
        this.serviceID = serviceID;
        this.roomID = roomID;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }
  public ServiceInvoice(String serviceID, int roomID, int quantity, double totalPrice) {
        this.serviceID = serviceID;
        this.roomID = roomID;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }


    public int getServiceInvoiceId() {
        return serviceInvoiceId;
    }

    public String getServiceID() {
        return serviceID;
    }

    public int getRoomID() {
        return roomID;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setServiceInvoiceId(int serviceInvoiceId) {
        this.serviceInvoiceId = serviceInvoiceId;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
    
}
