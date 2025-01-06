/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

/**
 *
 * @author quoct
 */
import java.util.EventListener;

// Interface định nghĩa sự kiện khi phòng được thêm
public interface RoomAddedListener extends EventListener {
    void roomAdded();
    void roomAddedTwoFloor();
    void roomRemove();
}
