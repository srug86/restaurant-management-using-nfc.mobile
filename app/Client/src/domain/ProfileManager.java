/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import communications.BluetoothClient;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import javax.bluetooth.BluetoothStateException;
import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;
import presentation.MobiCarta;

/**
 *
 * @author Sergio
 */
public class ProfileManager {
    
    public static String path = "file:///e:/mobiCarta/";
    
    public static String profile = "profile.xml", dni = "dni.txt";
    
    public static Client client = new Client();
    
    public static boolean sendProfile(String address, MobiCarta mbc) {
        String data = loadFile(profile);
        if (!data.equals("")) {
            try {
                BluetoothClient bc = BluetoothClient.getBluetoothClient();
                bc.sendData(address, data, 0, mbc);
                return true;
            } catch (BluetoothStateException ex) {
                return false;
            }
        }
        return false;
    }
    
    public static boolean saveProfile(Client c) {
        client = c;
        final String xml = xmlProfilesBuilder();
        return createFile(profile, xml) && createFile(dni, client.getDNI());
    }
    
    private static boolean createFile(String fileName, String data) {
        try{
            FileConnection fileCon = (FileConnection)Connector.open(path + fileName, Connector.READ_WRITE);
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
    
    public static String loadFile(String fileName) {
        try {
            FileConnection fileCon = (FileConnection)Connector.open(path + fileName,Connector.READ);
            if(fileCon.exists()){
                DataInputStream in = fileCon.openDataInputStream();
                byte [] datos = new byte[(int)fileCon.fileSize()];
                in.read(datos);
                in.close();
                //relleno el vector con los datos
                String datosPlain = new String(datos);
                fileCon.close();
                return datosPlain;
            }
            fileCon.close();
        } catch(Exception e) { }
        return "";
    }
    
    private static String xmlProfilesBuilder() {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
        xml += "<Profile>\n";
        if (client != null) {
            xml += "\t<DNI>" + client.getDNI() + "</DNI>\n";
            xml += "\t<Name>" + client.getName() + "</Name>\n";
            xml += "\t<Surname>" + client.getSurname() + "</Surname>\n";
            xml += "\t<Address>\n";
            if (client.getAddress() != null) {
                xml += "\t\t<Street>" + client.getAddress().getStreet() + "</Street>\n";
                xml += "\t\t<Number>" + client.getAddress().getNumber() + "</Number>\n";
                xml += "\t\t<ZipCode>" + client.getAddress().getZipCode() + "</ZipCode>\n";
                xml += "\t\t<Town>" + client.getAddress().getTown() + "</Town>\n";
                xml += "\t<State>" + client.getAddress().getState() + "</State>\n";
            }
            xml += "\t</Address>\n";
        }
        xml += "</Profile>";
        return xml;
    }
}
