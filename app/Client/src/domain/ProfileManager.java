/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import com.sun.midp.io.j2me.storage.File;
import communications.BluetoothClient;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import javax.bluetooth.BluetoothStateException;
import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;
import javax.microedition.lcdui.AlertType;
import presentation.MobiCarta;
import org.kxml2.io.KXmlParser;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
/**
 *
 * @author Sergio
 */
public class ProfileManager {
    
    public static String path = "file:///e:/mobiCarta/";
    
    public static String profile = "profile.xml";
    
    public static boolean sendProfile(String address, MobiCarta mbc) {
        String data = loadFile(profile);
        if (!data.equals("")) {
            try {
                BluetoothClient bc = BluetoothClient.getBluetoothClient();
                bc.sendData(address, data, 0, mbc);
                return true;
            } catch (BluetoothStateException ex) {
                mbc.showAlert("Error en la conexión Bluetooth", ex.getMessage(), AlertType.ERROR);
                return false;
            }
        }
        return false;
    }
    
    public static boolean saveProfile(Client c) {
        final String xml = xmlProfilesBuilder(c);
        return createFile(profile, xml);
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
    
    public static Client loadProfile(MobiCarta mbc) {
        return xmlProfilesDecoder(profile, mbc);
    }
    
    private static String xmlProfilesBuilder(Client client) {
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
    
    private static Client xmlProfilesDecoder(String fileName, MobiCarta mbc) {
        Client client = new Client();
        try {
            KXmlParser parser = new KXmlParser();
            FileConnection file = (FileConnection)Connector.open(path + fileName, Connector.READ);
            InputStreamReader is = new InputStreamReader(file.openInputStream());
            parser.setInput(is);
            parser.nextTag();
            parser.require(XmlPullParser.START_TAG, null, "Profile");
            while (parser.next() != XmlPullParser.END_DOCUMENT) {
                if (parser.getEventType() == XmlPullParser.START_TAG) {
                    if (parser.getName().equals("DNI"))
                        client.setDNI(parser.nextText());
                    else if (parser.getName().equals("Name"))
                        client.setName(parser.nextText());
                    else if (parser.getName().equals("Surname"))
                        client.setSurname(parser.nextText());
                    else if (parser.getName().equals("Street"))
                        client.getAddress().setStreet(parser.nextText());
                    else if (parser.getName().equals("Number"))
                        client.getAddress().setNumber(parser.nextText());
                    else if (parser.getName().equals("ZipCode"))
                        client.getAddress().setZipCode(Integer.parseInt(parser.nextText()));
                    else if (parser.getName().equals("Town"))
                        client.getAddress().setTown(parser.nextText());
                    else if (parser.getName().equals("State"))
                        client.getAddress().setState(parser.nextText());
                }
            }
            file.close();
        } catch (XmlPullParserException ex) {
            mbc.showAlert("Error en la conexión Bluetooth", ex.getMessage(), AlertType.ERROR);
        } catch (IOException ex) {
            mbc.showAlert("Error en la conexión Bluetooth", ex.getMessage(), AlertType.ERROR);
        } 
        return client;
    }
}
