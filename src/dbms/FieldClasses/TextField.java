/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dbms.FieldClasses;

import java.nio.ByteBuffer;

/**
 *
 * @author starwalkerholiday
 */
public class TextField {
    private short max_size;
    private char[] data;
    public ByteBuffer wrapData() {
        return ByteBuffer.allocate(4).putInt(data);
    }
}
