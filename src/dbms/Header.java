/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dbms;

import java.nio.ByteBuffer;

/**
 *
 * @author starwalkerholiday
 */
public class Header {
    
    /*
    Estructura de header [elemento(b)]:
    tamaño(2) -> nombre_de_archivo(variable) -> versión(2) -> cabeza_avail_list(4)
    */
    
    // Calcular
    private short size = 8;
    private char[] file_name;
    private short version;
    private int av_head = -1;
    
    public Header(String file_name, short version) {
        this.file_name = file_name.toCharArray();
        this.size += (short)(this.file_name.length * 2);
        this.version = version;
    }
    
    public ByteBuffer wrap() {
        
        ByteBuffer bb = ByteBuffer.allocate(size+1);
        
        bb.putShort(size);
        for (int i = 0; i < file_name.length; i++) {
            bb.putChar(file_name[i]);
        }
        bb.putShort(version);
        bb.putInt(av_head);
        
        return bb;
        
    }
    
}
