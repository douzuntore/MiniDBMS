/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dbms;

import dbms.FieldClasses.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

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
            
            bb = ByteBuffer.allocate(Byte.BYTES).position(0);
            fc.read(bb, position);
            bb.rewind();
            position++;
            byte field_amnt = (byte)(bb.get() - Byte.MIN_VALUE);
            Field[] fields = new Field[field_amnt];
            
            for (int i = 0; i < field_amnt; i++) {
                
                // leer longitud de bytes del campo
                bb = ByteBuffer.allocate(Integer.BYTES);
                fc.read(bb, position);
                int bytes_campo = bb.rewind().getInt();
                position += 4;
                //
                
                // leer campo
                bb = ByteBuffer.allocate(bytes_campo);
                fc.read(bb, position);
                bb.position(0);
                // es obligatorio?
                boolean opt = bb.get() == 01;
                // nombre de campo
                byte field_name_size = (byte)(bb.get() - Byte.MIN_VALUE);
                byte[] string_buffer = new byte[field_name_size];
                bb.get(string_buffer);
                String name = new String(string_buffer, StandardCharsets.UTF_8);
                // tipo de dato
                byte type = bb.get();
                
                // lectura de datos dependiendo del tipo
                Field new_field = null;
                switch (type) {
                    case 0:
                        int dft_data_0 = bb.getInt();
                        new_field = new IntField(name, opt, dft_data_0);
                        break;
                    case 1:
                        short max_size = bb.getShort();
                        short dft_val_size = (short)(bb.getShort() - Short.MIN_VALUE);
                        byte[] dft_val_buffer = new byte[dft_val_size];
                        bb.get(dft_val_buffer);
                        String dft_data_1 = new String(dft_val_buffer, StandardCharsets.UTF_8);
                        new_field = new TextField(name, opt, max_size, dft_data_1);
                        break;
                    case 2:
                        byte day = bb.get();
                        byte month = bb.get();
                        short year = (short)(bb.getShort() - Short.MIN_VALUE);
                        new_field = new DateField(name, opt, year, month, day);
                        break;
                    case 3:
                        int whole_p = bb.getInt();
                        int rational_p = bb.getInt();
                        new_field = new DecimalField(name, opt, whole_p, rational_p);
                        break;
                    case 4:
                        byte dft_data_4 = bb.get();
                        new_field = new BoolField(name, opt, dft_data_4 == 01);
                        break;
                }
                
                fields[i] = new_field;
                
                //reposicionar
                position += bb.capacity();
            }
            
            return fields;
            
        }
    }
    
}
