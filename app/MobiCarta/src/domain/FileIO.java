/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;

/**
 *
 * @author Sergio
 */
public class FileIO {
    
    // Crea un archivo con nombre 'file', en la ruta 'path' y con los datos 'data'
    public static boolean createFile(String path, String file, String data) {
        try{
            createFolder(path); // comprueba primero si el directorio existe
            FileConnection fileCon = (FileConnection)Connector.open(path + file, Connector.READ_WRITE);
            if (!fileCon.exists())
                fileCon.create();
            OutputStream outSt = fileCon.openOutputStream();
            PrintStream printSt = new PrintStream(outSt);
            printSt.println(data);
            outSt.close();
            fileCon.close();
            return true;
        } catch(IOException e) {
        } catch(SecurityException e) { }
        return false;
    }
    
    // Crea una carpeta en la ruta 'path'
    public static void createFolder(String path) {
        try {
            FileConnection fileCon = (FileConnection)Connector.open(path);
            if (!fileCon.exists())
                fileCon.mkdir();
            fileCon.close();
        } catch (IOException e) { }
    }
    
    // Carga los datos del archivo 'file' que está en el directorio 'path'
    public static String loadFile(String path, String file) {
        createFolder(path); // comprueba primero si el directorio existe
        String data = "";
        try {
            FileConnection fileCon = (FileConnection)Connector.open(path + file,Connector.READ);
            if(fileCon.exists()){
                DataInputStream in = fileCon.openDataInputStream();
                byte [] arrayData = new byte[(int)fileCon.fileSize()];
                in.read(arrayData);
                in.close();
                data = new String(arrayData);
            }
            fileCon.close();
        } catch(Exception e) { }
        return data;
    }
}
