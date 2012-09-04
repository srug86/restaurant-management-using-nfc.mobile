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
    
    // Crea un archivo en la ruta 'file' con los datos 'data'
    public static boolean createFile(String file, String data) {
        try{
            FileConnection fileCon = (FileConnection)Connector.open(file, Connector.READ_WRITE);
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
    
    // Carga los datos del archivo con ruta 'file'
    public static String loadFile(String file) {
        String data = "";
        try {
            FileConnection fileCon = (FileConnection)Connector.open(file,Connector.READ);
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
