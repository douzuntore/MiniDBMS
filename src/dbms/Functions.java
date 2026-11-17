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
            
            int position = 0;
            
            ByteBuffer bb = header.wrap();
            
            fc.write(bb, position);                                 
            
        }
    }
    
    static boolean isFileValid(File file) throws FileNotFoundException, IOException {
        try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
            
            FileChannel fc = raf.getChannel();
            
            ByteBuffer file_bb = ByteBuffer.allocate(6);
            fc.read(file_bb);
            
            ByteBuffer bb = ByteBuffer.allocate(6);
            bb.put(Header.SIGNATURE);
            bb.putShort(Header.VERSION);
            
            if (file_bb.compareTo(bb) != 0) {
                return false;
            }
            
        }
        return true;
    }
    
    static void writeFieldMetadata(File file, Field[] fields) throws FileNotFoundException, IOException, IllegalArgumentException {
        try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
            
            // no se pueden tener mas de 256 campos por registro !!
            
            FileChannel fc = raf.getChannel();
            
            // ir sobre el header
            int position = 10;
            ByteBuffer bb = ByteBuffer.allocate(Short.BYTES);
            fc.read(bb, position);
            bb.rewind();
            position += bb.getShort() - Short.MIN_VALUE + Short.BYTES;
            //
            
            // escritura de número de campos
            fc.write(
                    ByteBuffer.allocate(1).put((byte)(fields.length + Byte.MIN_VALUE)).rewind(), position
            );
            position++;
            
            for (int i = 0; i < fields.length; i++) {
                ByteBuffer[] field_bb = fields[i].wrapFieldData();
                
                // seek & escritura
                fc.position(position);
                fc.write(field_bb);  
                
                // calcular siguiente espacio libre
                for (int j = 0; j < field_bb.length; j++) {
                    position += field_bb[j].capacity();
                }
            }                       
            
        }
    }
    
    static Field[] getFieldsFromMetadata(File file) throws FileNotFoundException, IOException, IllegalArgumentException {
        try (RandomAccessFile raf = new RandomAccessFile(file, "r")) {
            
            FileChannel fc = raf.getChannel();
            
            // ir sobre el header
            int position = 10;
            ByteBuffer bb = ByteBuffer.allocate(Short.BYTES);
            fc.read(bb, position);
            bb.rewind();
            position += bb.getShort() - Short.MIN_VALUE + Short.BYTES;
            //
            
            byte field_amnt = (byte)(bb.get(position) - Byte.MIN_VALUE);
            position++;
            Field[] fields = new Field[field_amnt];
            
            // TODO doug doug
            
            return fields;
            
        }
    }
    
}
