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
    private int dft_data;

    public DateField(String name, boolean opt, short year, byte month, byte day) {
        super(name, opt);
    }
    
    @Override
    public byte getFieldType() {
        return 02;
    }
    
    @Override
    public ByteBuffer[] wrapHeaderData() {
        
        ByteBuffer bb = ByteBuffer.allocate(sub_fixed_size);
        bb.put(getFieldType());
        bb.putInt(dft_data);
        
        return new ByteBuffer[]{
                getGeneralData(),
                bb
        };
        
    }
    
}
