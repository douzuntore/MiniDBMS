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
public class IntField extends Field {
    
    // datos que NO serán escritos en encabezado !!
    private static final byte sub_fixed_size = 5;
    
    // datos escritos en encabezado !!
    private int dft_data;

    public IntField(String name, boolean opt, int data) {
        super(name, opt);
        
        this.dft_data = data;
        
    }
    
    @Override
    public byte getFieldType() {
        return 00;
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
