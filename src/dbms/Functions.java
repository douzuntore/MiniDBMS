/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dbms;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 *
 * @author starwalkerholiday
 */
public class Functions {
    
    static void writeHeader(File file, Header header) throws FileNotFoundException, IOException {
        try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
            FileChannel fc = raf.getChannel();
            fc.write(header.wrap().flip(), 0);
        }
    }
    
}
