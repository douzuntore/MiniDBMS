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
public abstract class Field {
    
    // datos que NO serán escritos en encabezado !!
    private static final byte FIXED_SIZE = 2;
    
    // datos escritos en encabezado !!
    protected byte opt;
    protected int total_size = 0;
    protected byte name_size; //cantidad de caracteres; no bytes
    protected String name;
    
    public abstract ByteBuffer[] wrapFieldData();
    protected abstract byte getFieldType();

    public Field(String name, boolean opt) throws IllegalArgumentException {
        
        this.name_size = (byte)(name.getBytes().length + Byte.MIN_VALUE);
        
        if (name_size - Byte.MIN_VALUE > 256) {
            throw new IllegalArgumentException("Nombre muy grande");
        }
        
        this.opt = (byte)(opt ? 1 : 0);
        
        this.name = name;
        
    }
    
    protected ByteBuffer getGeneralData() {
        
        ByteBuffer bb = ByteBuffer.allocate(FIXED_SIZE + (name_size - Byte.MIN_VALUE));
        bb.put(opt);
        bb.put(name_size);
        bb.put(name.getBytes());
        total_size += bb.capacity();
        return bb;
        
    }
    
}
