/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dbms;

import dbms.FieldClasses.Field;
import java.nio.ByteBuffer;
import java.util.ArrayList;

/**
 *
 * @author starwalkerholiday
 */
public class Header {
    
    /*
    Estructura de header [elemento(b)]:
    tamaño(2) -> nombre_de_archivo(variable) -> versión(2) -> cabeza_avail_list(4)
    */
    private static final int fixed_size = 8;
    
    private short version;
    private int av_head = -1;
    private short f_name_size;
    private char[] file_name;
    
    private ArrayList<Field> fields = new ArrayList<Field>();
    
    public Header(String file_name, short version) {
        this.file_name = file_name.toCharArray();
        this.f_name_size += (short)(this.file_name.length * 2);
        this.version = version;
    }

    public ArrayList<Field> getFields() {
        return fields;
    }
    
    public ByteBuffer wrap() {
        
        ByteBuffer bb = ByteBuffer.allocate(fixed_size + 2*f_name_size);
        
        bb.putShort(version);
        bb.putInt(av_head);
        bb.putShort(f_name_size);
        for (int i = 0; i < file_name.length; i++) {
            bb.putChar(file_name[i]);
        }
        
        return bb;
        
    }
    
    public void addField(Field field) {
        fields.add(field);
    }
    
}
