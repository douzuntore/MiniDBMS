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
public class BoolField extends Field {
    
    // datos que NO serán escritos en encabezado !!
    private static final byte sub_fixed_size = 2;
    
    // datos escritos en encabezado !!
    private byte dft_data;

    public BoolField(String name, boolean opt, boolean data) {
        super(name, opt);
        
        this.dft_data = (byte)(data ? 1 : 0);
    }
    
    @Override
    public byte getFieldType() {
        return 04;
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
