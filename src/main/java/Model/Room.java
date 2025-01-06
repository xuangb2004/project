package Model;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author quoct
 */
public class Room {
    private int idRoom;
    private String nameRoom;
    private int roomType;
    private String status;
    private int quantity;
    private int area;
    private int price;
    private String descriptionRoom;
    private String location;

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public Room(int idRoom, String nameRoom,String descriptionRoom, String status, int quantity, int area, int price,String location) {
        this.idRoom = idRoom;
        this.nameRoom = nameRoom;
        this.status = status;
        this.quantity = quantity;
        this.area = area;
        this.price = price;
        this.descriptionRoom = descriptionRoom;
        this.location = location;
    }

    public Room(int idRoom, String nameRoom, int roomType, String status, int quantity, int area, int price,String location) {
        this.idRoom = idRoom;
        this.nameRoom = nameRoom;
        this.roomType = roomType;
        this.status = status;
        this.quantity = quantity;
        this.area = area;
        this.price = price;
        this.location = location;
    }

    public void setDescriptionRoom(String descriptionRoom) {
        this.descriptionRoom = descriptionRoom;
    }

    public String getDescriptionRoom() {
        return descriptionRoom;
    }

    public int getIdRoom() {
        return idRoom;
    }

    public String getNameRoom() {
        return nameRoom;
    }

    public int getRoomType() {
        return roomType;
    }

    public String getStatus() {
        return status;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getArea() {
        return area;
    }

    public int getPrice() {
        return price;
    }

    public void setIdRoom(int idRoom) {
        this.idRoom = idRoom;
    }

    public void setNameRoom(String nameRoom) {
        this.nameRoom = nameRoom;
    }

    public void setRoomType(int roomType) {
        this.roomType = roomType;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    
  
}
