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
public class TextField extends Field {
    
    // datos que NO serán escritos en encabezado !!
    private static final byte sub_fixed_size = 5;
    
    // datos escritos en encabezado !!
    private final short max_size;
    private final short dft_data_size;
    private final char[] dft_data;

    public TextField(String name, boolean opt, int max_size, String data) {
        super(name, opt);
        
        if (max_size > 65536) {
            throw new IllegalArgumentException("Tamaño máximo fuera de rango");
        }
        
        this.max_size = (short)(data.length() - 32768);
        
        if (data.length() > (max_size + 32768)) {
            throw new IllegalArgumentException("Texto muy grande");
        }
        
        dft_data_size = (short)(data.length() - 32768);
        this.dft_data = data.toCharArray();
        
    }
    
    @Override
    public byte getFieldType() {
        return 01;
    }
    
    @Override
    public ByteBuffer[] wrapHeaderData() {
        
        ByteBuffer bb = ByteBuffer.allocate(sub_fixed_size + 2*(dft_data_size + 32768));
        bb.put(getFieldType());
        bb.putShort(max_size);
        bb.putShort(dft_data_size);
        for (int i = 0; i < dft_data.length; i++) {
            bb.putChar(dft_data[i]);
        }
        return new ByteBuffer[]{
                getGeneralData(),
                bb
        };
        
    }
}
