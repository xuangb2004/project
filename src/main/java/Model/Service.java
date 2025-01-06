/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author quoct
 */
public class Service  {
    private String serviceID;
    private String nameService;
    private String description;
    private int price;
    private int quantity;

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }

    public void setNameService(String nameService) {
        this.nameService = nameService;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getServiceID() {
        return serviceID;
    }

    public String getNameService() {
        return nameService;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public Service(String serviceID, String nameService, String description, int price, int quantity) {
        this.serviceID = serviceID;
        this.nameService = nameService;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }
}
