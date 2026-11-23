package dbms;

import dbms.FieldClasses.Field;
import java.nio.ByteBuffer;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author starwalkerholiday
 */
public class IndexHeader {
    
    private static final int FIXED_SIZE = 5;
    
    private short f_name_size;
    private String file_name;
    
    private byte order = 6;
    
    private byte field_size;
    private String field_name;
    
    private boolean is_unique;
    
    public IndexHeader(String file_name, String field_name, boolean is_unique) {
        
        this.f_name_size = (short)(file_name.getBytes().length - Short.MIN_VALUE);
        this.file_name = file_name;
        
        this.field_size = (byte)(field_name.getBytes().length - Byte.MIN_VALUE);
        this.field_name = field_name;
        
        this.is_unique = is_unique;
        
    }
    
    public IndexHeader(String file_name, Field field, boolean is_unique) {
        
        this.f_name_size = (short)(file_name.getBytes().length - Short.MIN_VALUE);
        this.file_name = file_name;
        
        String field_name = field.getName();
        this.field_size = (byte)(field_name.getBytes().length - Byte.MIN_VALUE);
        this.field_name = field_name;
        
        this.is_unique = is_unique;
        
    }
    
    public ByteBuffer wrap() {
        
        int header_size = FIXED_SIZE + (f_name_size - Short.MIN_VALUE) + (field_size - Byte.MIN_VALUE);
        ByteBuffer bb = ByteBuffer.allocate(header_size);
        
        bb.putShort(f_name_size);
        bb.put(file_name.getBytes());
        
        bb.put(order);
                
        bb.put(field_size);
        bb.put(field_name.getBytes());
        
        bb.put((byte)(is_unique ? 1 : 0));
        
        return bb.rewind();
        
    }
    
}
