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
public class DateField extends Field {
    
    // fecha en formato epoch
    // TODO logica de traducción de dd/mm/yyyy a epoch
    
    // datos que NO serán escritos en encabezado !!
    private static final byte sub_fixed_size = 5;
    
    // datos escritos en encabezado !!
    private byte day;
    private byte month;
    private short year;

    public DateField(String name, boolean opt, short year, byte month, byte day) {
        super(name, opt);
        
        if (day < 0 || day > 31) {
            throw new IllegalArgumentException("Dia fuera de rango");
        }
        if (month < 0 || month > 12) {
            throw new IllegalArgumentException("Mes fuera de rango");
        }
        if (month <= 0 || month > Short.MAX_VALUE) {
            throw new IllegalArgumentException("Mes fuera de rango");
        }
        
        this.day = day;
        this.month = month;
        this.year = year;
    }
    
    @Override
    public byte getFieldType() {
        return 02;
    }
    
    @Override
    public ByteBuffer[] wrapFieldData() {
        
        total_size = 0;
        
        ByteBuffer bb = ByteBuffer.allocate(sub_fixed_size).position(0);
        bb.put(getFieldType());
        bb.put(day);
        bb.put(month);
        bb.putShort((short)(year + Short.MIN_VALUE));
        total_size += bb.capacity() + Integer.BYTES;
        
        ByteBuffer gen_data_bb = getGeneralData();
        
        return new ByteBuffer[]{
                ByteBuffer.allocate(4).putInt(total_size).rewind(),
                gen_data_bb.rewind(),
                bb.rewind()
        };
        
    }
    
}
