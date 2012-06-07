/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package communications;

import domain.RecommendationManager;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DeviceClass;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.DiscoveryListener;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.ServiceRecord;
import javax.bluetooth.UUID;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.lcdui.AlertType;
import presentation.MobiCarta;

/**
 *
 * @author Sergio
 */
public class BluetoothClient implements DiscoveryListener {

    public static final UUID RECEIVER_SERVICE = new UUID("888794c265ce4de1aa1574a11342bc63", false);
    public static final UUID BAR_SERVICE = new UUID("888794c265ce4de1aa1574a11342bc64", false);
    public static UUID[] SERVICES;
    
    public static final int[] ATRIBUTES = null;
    
    private Vector searches;
    private DiscoveryAgent discoveryAgent;
    private LocalDevice localDevice;
    
    private MobiCarta mbc;
    
    private String data;
    private int rcv;
    
    private static BluetoothClient bc = null;
    
    public BluetoothClient() { }
    
    public static BluetoothClient getBluetoothClient() {
        if (bc == null)
            bc = new BluetoothClient();
        return bc;
    }
    
    private void clientStart() throws BluetoothStateException {
        searches = new Vector();
        localDevice = null;
        localDevice = LocalDevice.getLocalDevice();
        localDevice.setDiscoverable(DiscoveryAgent.GIAC);
        discoveryAgent = localDevice.getDiscoveryAgent();
    }
    
    public void sendData(String srvAddress, String data, int rcv, MobiCarta mbc) throws BluetoothStateException {
        this.mbc = mbc;
        this.rcv = rcv;
        this.data = data;
        if (rcv == 0)
            SERVICES = new UUID[] { RECEIVER_SERVICE };
        else 
            SERVICES = new UUID[] { BAR_SERVICE };
        clientStart();
        discoveryAgent.startInquiry(DiscoveryAgent.GIAC, this);
    }
    
    private void finishSearches() {
        discoveryAgent.cancelInquiry(this);
        Enumeration en = searches.elements();
        Integer i;
        while (en.hasMoreElements()) {
            i = (Integer) en.nextElement();
            discoveryAgent.cancelServiceSearch(i.intValue());
        }
    }
    
    public void deviceDiscovered(RemoteDevice rd, DeviceClass dc) {
        String address = rd.getBluetoothAddress();
        String friendlyName = null;
        try {
            friendlyName = rd.getFriendlyName(true);
        } catch(IOException e) { }
        String device = null;
        if(friendlyName == null) {
            device = address;
        } else {
            device = friendlyName + " ("+address+")";
        }
        try {
            int transId = discoveryAgent.searchServices(ATRIBUTES, SERVICES, rd, this);
            searches.addElement(new Integer(transId));
            //if (srvAddress.equals(address))
            //{
                //Alert alert = new Alert("Dispositivo", device, null, AlertType.CONFIRMATION);
                //Display.getDisplay(mbc).setCurrent(alert);
            //}
        } catch(BluetoothStateException e) {
            mbc.showAlert("Error en la conexión Bluetooth", e.getMessage(), AlertType.ERROR);
        }
    }

    public void servicesDiscovered(int transId, ServiceRecord[] srs) {
        ServiceRecord service = null;
        for (int i = 0; i < srs.length; i++) {
            service = srs[i];
            String url = service.getConnectionURL(ServiceRecord.NOAUTHENTICATE_NOENCRYPT, false);
            StreamConnection connection = null;
            DataInputStream in = null;
            DataOutputStream out = null;
            String reply = "";
            byte[] r = new byte[5120];
            try {
                connection = (StreamConnection)Connector.open(url);
                //out = connection.openDataOutputStream();
                in = connection.openDataInputStream();
                //out.writeUTF(data);
                //out.flush();
                /*do {
                    reply += in.readUTF();
                } while(!reply.endsWith("</Recommendations>"));*/
                int rd = 0;
                //byte[] r = new byte[5120];
                while( (rd = in.read(r)) <= 0){};
            } catch (IOException e) {
                mbc.showAlert("Error de entrada/salida", "Holaaaa!!!", AlertType.ERROR);
            } finally {
                try {
                    if (in != null) in.close();
                    if (out != null) out.close();
                    if (connection != null) connection.close();
                    if (rcv == 0) {
                        mbc.showAlert("Salida", new String(r), AlertType.CONFIRMATION);
                    }
                    /*if (rcv == 0) {
                        RecommendationManager.catchRecommendation(reply, mbc);
                        mbc.getDisplay().setCurrent(mbc.getCheckpoint(), mbc.getOpening());
                    }*/
                    else mbc.showAlert("Solicitud de pedidos", "Su pedido está en camino", AlertType.CONFIRMATION);
                    finishSearches();
                } catch (IOException e) {
                    mbc.showAlert("Error de entrada/salida", e.getMessage(), AlertType.ERROR);
                }
            }
        }
    }

    public void serviceSearchCompleted(int i, int i1) {
        System.out.println("Búsqueda de servicios " + i + " completada");
        switch (i1) {
            case DiscoveryListener.SERVICE_SEARCH_COMPLETED:
                System.out.println("Búsqueda completada con normalidad");
                break;
            case DiscoveryListener.SERVICE_SEARCH_TERMINATED:
                System.out.println("Búsqueda cancelada");
                break;
            case DiscoveryListener.SERVICE_SEARCH_DEVICE_NOT_REACHABLE:
                System.out.println("Dispositivo no alcanzable");
                break;
            case DiscoveryListener.SERVICE_SEARCH_NO_RECORDS:
                System.out.println("No se encontraron registros de servicio");
                break;
            case DiscoveryListener.SERVICE_SEARCH_ERROR:
                System.out.println("Error en la búsqueda");
                break;
        }
    }

    public void inquiryCompleted(int i) {
        switch (i) {
            case DiscoveryListener.INQUIRY_COMPLETED:
                System.out.println("Búsqueda de dispositivos concluida con normalidad");
                break;
            case DiscoveryListener.INQUIRY_TERMINATED:
                System.out.println("Búsqueda de dispositivos cancelada");
                break;
            case DiscoveryListener.INQUIRY_ERROR:
                System.out.println("Búsqueda de dispositivos finalizada debido a un error");
                break;
        }
    }
    
}
