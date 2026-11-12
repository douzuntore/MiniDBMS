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
    private static final byte fixed_size = 2;
    
    // datos escritos en encabezado !!
    protected byte opt;
    protected byte name_size; //cantidad de caracteres; no bytes
    protected char[] name;
    
    public abstract ByteBuffer[] wrapHeaderData();
    protected abstract byte getFieldType();

    public Field(String name, boolean opt) throws IllegalArgumentException {
        
        if (name.length() > 256) {
            throw new IllegalArgumentException("Nombre muy grande");
        }
        
        this.opt = (byte)(opt ? 1 : 0);
        
        this.name_size = (byte)(name.length() - 128);
        this.name = name.toCharArray();
        
    }
    
    protected ByteBuffer getGeneralData() {
        
        ByteBuffer bb = ByteBuffer.allocate(fixed_size + 2*(name_size + 128));
        bb.put(opt);
        bb.put(name_size);
        for (int i = 0; i < name.length; i++) {
            bb.putChar(name[i]);
        }
        return bb;
        
    }
    
}
