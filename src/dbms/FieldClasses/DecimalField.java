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
public class DecimalField extends Field {
    
    // datos que NO serán escritos en encabezado !!
    private static final byte sub_fixed_size = 9;
    
    // datos escritos en encabezado !!
    private int whole_p;
    private int rational_p;
    //cambios jafeth
    private double dft_data;

    public DecimalField(String name, boolean opt, int whole_p, int rational_p) {
        super(name, opt);
        
        this.whole_p = whole_p;
        this.rational_p = rational_p;
    }

    public DecimalField(String name,  boolean opt, double dft_data) throws IllegalArgumentException {
        super(name, opt);
        this.dft_data = dft_data;
    }
    
    
    
    @Override
    public byte getFieldType() {
        return 03;
    }

    @Override
    public ByteBuffer[] wrapFieldData() {
        
        total_size = 0;
        
        ByteBuffer bb = ByteBuffer.allocate(sub_fixed_size).position(0);
        bb.put(getFieldType());
        bb.putDouble(dft_data);
//        bb.putInt(whole_p);
//        bb.putInt(rational_p);
        total_size += bb.capacity();
        
        ByteBuffer gen_data_bb = getGeneralData();
        
        return new ByteBuffer[]{
                ByteBuffer.allocate(4).putInt(total_size).rewind(),
                gen_data_bb.rewind(),
                bb.rewind()
        };
        
    }
    
    
    
}
