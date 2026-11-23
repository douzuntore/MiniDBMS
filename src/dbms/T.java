package dbms;

import dbms.FieldClasses.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
//clase para pruebas
public class T {
    public static void main(String[] args) throws Exception {
        //prueba de archivos
       pruebaArchivos();
    }
    
    public static void pruebaArchivos() throws IOException{
     // un archivo ajeno debe ser rechazado
        File x = new File("ajeno.bin");
        Files.write(x.toPath(), "esto no es  rwrefbceuywsdou".getBytes());
        System.out.println("archivo  rechazado: " + !Functions.isFileValid(x));

        // el tamaño de Texto debe sobrevivir varios ciclos abrir y guardar
        File f = new File("t.disv");
        f.delete();
        f.createNewFile();
        Functions.writeHeader(f, new Header("t"));
        Functions.writeFieldMetadata(f, new Field[]{
            new DecimalField("id", false, 10.5),
            new TextField("corto", true, 50, "hola"),
            new TextField("largo", true, 40000, "x".repeat(35000))
        });

        byte[] antes = Files.readAllBytes(f.toPath());
        for (int i = 1; i <= 3; i++) {
            Field[] fs = Functions.getFieldsFromMetadata(f);
            Functions.writeFieldMetadata(f, fs);
            byte[] despues = Files.readAllBytes(f.toPath());
            System.out.println("ciclo " + i + " sin cambios: " + java.util.Arrays.equals(antes, despues));
        }
    
    }
}