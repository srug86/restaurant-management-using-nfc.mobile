/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.OutputStream;
import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;

/**
 *
 * @author Sergio
 */
public class ProfileManager {
    
    private static String path = "file:////profile.xml";
    
    public static Client client = new Client();
    
    public static boolean saveProfile(Client c) {
        client = c;
        String xml = xmlProfilesBuilder();
        return createFile(xml);
    }
    
    private static String xmlProfilesBuilder() {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
        xml += "<Profile>\n";
        if (client != null) {
            xml += "<DNI>" + client.getDNI() + "</DNI>\n";
            xml += "<Name>" + client.getName() + "</Name>\n";
            xml += "<Surname>" + client.getSurname() + "</Surname>\n";
            xml += "<Address>\n";
            if (client.getAddress() != null) {
                xml += "<Street>" + client.getAddress().getStreet() + "</Street>\n";
                xml += "<Number>" + client.getAddress().getNumber() + "</Number>\n";
                xml += "<ZipCode>" + client.getAddress().getZipCode() + "</ZipCode>\n";
                xml += "<Town>" + client.getAddress().getTown() + "</Town>\n";
                xml += "<State>" + client.getAddress().getState() + "</State>\n";
            }
            xml += "</Address>\n";
        }
        xml += "</Profile>";
        return xml;
    }
    
    private static boolean createFile(String data) {
        try {
            FileConnection fileCon = (FileConnection)Connector.open(path, Connector.WRITE);
            if (!fileCon.exists())
                fileCon.create();
            OutputStream os = fileCon.openOutputStream();
            os.write(data.getBytes());
            os.close();
            fileCon.close();
            return true;
        } catch (Exception e) { 
            e.printStackTrace();
            return false; }
    }
}
