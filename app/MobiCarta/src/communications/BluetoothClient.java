/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package communications;

import domain.BillManager;
import domain.RecommendationManager;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Enumeration;
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

    // identificador del servicio del recibidor
    public static final UUID RECEIVER_SERVICE = new UUID("888794c265ce4de1aa1574a11342bc63", false);
    // identificador del servicio de la barra
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
    
    /* Implementación del patrón 'Singleton' para la clase */
    public BluetoothClient() { }
    
    public static BluetoothClient getBluetoothClient() {
        if (bc == null)
            bc = new BluetoothClient();
        return bc;
    }
    
    // Inicializa los valores de la clase
    public void sendData(String srvAddress, String data, int rcv, MobiCarta mbc) throws BluetoothStateException {
        this.mbc = mbc;
        this.rcv = rcv;
        this.data = data;
        if (rcv == 0) SERVICES = new UUID[] { RECEIVER_SERVICE };
        else SERVICES = new UUID[] { BAR_SERVICE };
        clientStart();
    }
    
    // Inicializa la búsqueda de servicios Bluetooth
    private void clientStart() throws BluetoothStateException {
        searches = new Vector();
        localDevice = null;
        localDevice = LocalDevice.getLocalDevice();
        localDevice.setDiscoverable(DiscoveryAgent.GIAC);
        discoveryAgent = localDevice.getDiscoveryAgent();
        discoveryAgent.startInquiry(DiscoveryAgent.GIAC, this);
    }
    
    // Finaliza la búsqueda de servicios Bluetooth
    private void finishSearches() {
        discoveryAgent.cancelInquiry(this);
        Enumeration en = searches.elements();
        Integer i;
        while (en.hasMoreElements()) {
            i = (Integer) en.nextElement();
            discoveryAgent.cancelServiceSearch(i.intValue());
        }
    }
    
    // Almacena la lista de dispositivos encontrados
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
            // Inicia la búsqueda de servicios en el dispositivo encontrado, en
            // búsqueda de los servicios especificados
            int transId = discoveryAgent.searchServices(ATRIBUTES, SERVICES, rd, this);
            searches.addElement(new Integer(transId));
        } catch(BluetoothStateException e) {
            mbc.showAlert("Error en la conexión Bluetooth", e.getMessage(), AlertType.ERROR);
        }
    }

    // Gestiona las comunicaciones con el servicio Bluetooth encontrado
    public void servicesDiscovered(int transId, ServiceRecord[] srs) {
        ServiceRecord service = null;
        for (int i = 0; i < srs.length; i++) {
            service = srs[i];
            String url = service.getConnectionURL(ServiceRecord.NOAUTHENTICATE_NOENCRYPT, false);
            StreamConnection connection = null;
            DataInputStream in = null;
            DataOutputStream out = null;
            byte[] reply = new byte[20480];
            try {
                connection = (StreamConnection)Connector.open(url); // Establece conexión con el servidor
                out = connection.openDataOutputStream();
                in = connection.openDataInputStream();
                mbc.getGauge().setLabel("Enviando datos...");
                out.writeUTF(data); // Envía los datos al servidor
                out.flush();
                int rd = 0;
                mbc.getGauge().setLabel("Recibiendo respuesta...");
                while ((rd = in.read(reply)) <= 0); // Recibe la respuesta de este
            } catch (IOException e) {
                mbc.showAlert("Error de entrada/salida", e.toString(), AlertType.ERROR);
            } finally {
                try {
                    mbc.getGauge().setLabel("Cerrando conexión...");
                    // Cierra los Streams y la conexión activa
                    if (in != null) in.close();
                    if (out != null) out.close();
                    if (connection != null) { connection.close(); connection = null; }
                    String msg = new String(reply, "ASCII");
                    switch (rcv) {
                        case 0: // El cliente recibe las recomendaciones personalizadas
                            if (msg.substring(0, 1).equals("<")) {
                                RecommendationManager.catchRecommendation(msg, mbc);
                                mbc.getDisplay().setCurrent(mbc.getCheckpoint(), mbc.getOpening());
                            }
                            else
                                mbc.showAlert("Registro de usuarios", msg, AlertType.INFO);
                            break;
                        case 1: // El cliente recibe la confirmación de "pedido en camino"
                            if (msg.substring(0, 5).equals("ERROR"))
                                mbc.showAlert("Solicitud de pedidos", msg, AlertType.ERROR);
                            else mbc.showAlert("Solicitud de pedidos", msg, AlertType.CONFIRMATION);
                            break;
                        case 2: // El cliente recibe la factura solicitada
                            BillManager.catchBill(msg, mbc);
                            mbc.getDisplay().setCurrent(mbc.getBill());
                            break;
                        default:
                            mbc.showAlert("Operación no válida", "La operación no se pudo completar", AlertType.ERROR);
                            break;
                    }
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
