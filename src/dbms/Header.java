/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dbms;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author starwalkerholiday
 */
public class Header {
    
    /*
    Estructura de header [elemento(b)]:
    tamaño(2) -> nombre_de_archivo(variable) -> versión(2) -> cabeza_avail_list(4)
    */
    private static final int FIXED_SIZE = 13;
    public static final byte[] SIGNATURE = {18, 84, -47, 21};
    public static final short VERSION = 7;
    
    private int av_head = -1;
    private short f_name_size;
    private String file_name;
    
    public Header(String file_name) {
        this.file_name = file_name;
        this.f_name_size += (short)(this.file_name.getBytes(StandardCharsets.UTF_8).length + Short.MIN_VALUE);
    }
    
    public ByteBuffer wrap() {
        
        int header_size = FIXED_SIZE + (f_name_size - Short.MIN_VALUE);
        ByteBuffer bb = ByteBuffer.allocate(header_size);
       
        
        //escritura de firma
        bb.put(SIGNATURE);
        bb.putShort(VERSION);
        
        bb.putInt(av_head);
        bb.putShort(f_name_size);
        
        bb.put(file_name.getBytes(StandardCharsets.UTF_8));
        
        bb.put((byte)(-128));
        
        return bb.rewind();
        
    }
    
}
