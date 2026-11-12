/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dbms;

import dbms.FieldClasses.Field;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 *
 * @author starwalkerholiday
 */
public class Functions {
    
    static void writeHeader(File file, Header header) throws FileNotFoundException, IOException {
        try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
            
            FileChannel fc = raf.getChannel();
            
            int position = 4;
            //escritura de firma
            fc.write(ByteBuffer.wrap(new byte[]{18, 84, -47, 21}));
            
            ByteBuffer bb = header.wrap().flip();
            
            fc.write(bb, 0);
            position += bb.capacity();
            
            for (Field field : header.getFields()) {
                ByteBuffer[] bbs = field.wrapHeaderData();
                for (int i = 0; i < bbs.length; i++) {
                    bbs[i].flip();
                    fc.write(bbs[i], position);
                    position += bbs[i].capacity();
                }
            }
            
        }
    }
    
}
