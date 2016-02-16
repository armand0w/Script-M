package com.arm.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by ACsatillo on 16/02/2016.
 */

public class Utilities {
    public static void saveDocument(String doc, String name){
        String path = "web/docs/";
        FileWriter fw = null;
        PrintWriter pw = null;
        name = name.replaceAll(" ", "");
        try{
            if( !new File( path+name ).exists() ) {
                fw = new FileWriter(path+name);
                pw = new PrintWriter(fw);
                pw.print(doc);
                System.out.println("Archivo Guardado");
            } else System.out.println("El archivo ya existe");
        } catch ( IOException io){ System.err.println("Error al guardar archivo '" + name + "' = " + io.getLocalizedMessage()); }
        finally {
            try {
                if (pw != null) pw.close();
                if (fw != null) fw.close();
            } catch (IOException e){ System.err.println("Error al cerrar archivo"); }
        }
    }
}
