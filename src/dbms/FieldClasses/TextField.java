/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dbms.FieldClasses;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author starwalkerholiday
 */
public class TextField extends Field {
    
    // datos que NO serán escritos en encabezado !!
    private static final byte sub_fixed_size = 5;
    
    // datos escritos en encabezado !!
    private final short max_size; 
    private final short dft_data_size;
    private final String dft_data;

    public TextField(String name, boolean opt, int max_size, String data) {
        super(name, opt);
        
        if (max_size < 1 || max_size > 65535) {
            throw new IllegalArgumentException("Tamaño máximo fuera de rango");
        }
        
        this.max_size = (short)(max_size + Short.MIN_VALUE);
        
        //if (data.length() > (max_size - Short.MIN_VALUE)) {
        if (data.getBytes(StandardCharsets.UTF_8).length > max_size) {
            throw new IllegalArgumentException("Texto muy grande");
        }
        
        dft_data_size = (short)(data.getBytes(StandardCharsets.UTF_8).length + Short.MIN_VALUE);
        
        this.dft_data = data;
        
    }
    
    @Override
    public byte getFieldType() {
        return 01;
    }
    
    @Override
    public ByteBuffer[] wrapFieldData() {
        
        total_size = 0;
        
        ByteBuffer bb = ByteBuffer.allocate(sub_fixed_size + (dft_data_size - Short.MIN_VALUE )).position(0);
        bb.put(getFieldType());
        bb.putShort(max_size);
        bb.putShort(dft_data_size);
        bb.put(dft_data.getBytes(StandardCharsets.UTF_8));
        total_size += bb.capacity();
        
        ByteBuffer gen_data_bb = getGeneralData();
        
        return new ByteBuffer[]{
            ByteBuffer.allocate(4).putInt(total_size).rewind(),
            gen_data_bb.rewind(),
            bb.rewind()
        };
        
    }

    public short getMax_size() {
        return max_size;
    }

    public String getDft_data() {
        return dft_data;
    }
}
