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
    
    // Directorio donde se almacena el perfil del cliente
    public static String path = "file:///e:/mobiCarta/profile.xml";
    
    // Envía el perfil del cliente al recibidor
    public static boolean sendProfile(String address, MobiCarta mbc) {
        String data = FileIO.loadFile(path);    // Carga el perfil almacenado
        if (!data.equals("")) {
            try {
                BluetoothClient bc = BluetoothClient.getBluetoothClient();
                bc.sendData(address, data, 0, mbc); // y se lo envía
                return true;
            } catch (BluetoothStateException ex) {
                mbc.showAlert("Error en la conexión Bluetooth", ex.getMessage(), AlertType.ERROR);
                return false;
            }
        }
        return false;
    }
    
    // Guarda el perfil del cliente en un archivo
    public static boolean saveProfile(Client c) {
        final String xml = xmlProfileBuilder(c);
        return FileIO.createFile(path, xml);
    }
    
    // Carga el perfil del cliente de un archivo previamente almacenado
    public static Client loadClient(MobiCarta mbc) {
        return xmlProfileDecoder(path, mbc);
    }
    
    // Codifica en XML el perfil del cliente
    private static String xmlProfileBuilder(Client client) {
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
    
    // Decodifica el XML con el perfil del cliente
    private static Client xmlProfileDecoder(String file, MobiCarta mbc) {
        Client client = new Client();
        KXmlParser parser = new KXmlParser();
        try {
            // El perfil se carga del archivo generado con anterioridad
            FileConnection fileCon = (FileConnection)Connector.open(file, Connector.READ);
            if (fileCon.exists()) {
                InputStreamReader is = new InputStreamReader(fileCon.openInputStream());
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
                is.close();
            }
            fileCon.close();
        } catch (XmlPullParserException ex) {
            mbc.showAlert("Error en la estructura del documento", ex.getMessage(), AlertType.ERROR);
        } catch (IOException ex) {
            mbc.showAlert("Error de entrada/salida", ex.getMessage(), AlertType.ERROR);
        }
        return client;
    }
}
