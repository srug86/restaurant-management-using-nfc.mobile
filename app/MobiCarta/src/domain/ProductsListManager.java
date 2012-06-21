/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import communications.BluetoothClient;
import java.util.Vector;
import javax.bluetooth.BluetoothStateException;
import javax.microedition.lcdui.AlertType;
import presentation.MobiCarta;

/**
 *
 * @author Sergio
 */
public class ProductsListManager {
    public static Vector productsList = new Vector();
    
    public static String addProduct(String product) {
        String prom = "";
        if (!(prom = RecommendationManager.getPromotion(product)).equals(""))
            prom = "\t\t(" + prom + ")";
        for (int i = 0; i < productsList.size(); i++)
            if (productsList.elementAt(i).equals(new Order(product, 0))) {
                ((Order)productsList.elementAt(i)).setAmount(((Order)productsList.
                        elementAt(i)).getAmount() + 1);
                return ((Order)productsList.elementAt(i)).getAmount() + " " + prom;
            }
        productsList.addElement(new Order(product, 1));
        return 1 + " " + prom;
    }
    
    public static int removeProduct(String product) {
        for (int i = 0; i < productsList.size(); i++)
            if (productsList.elementAt(i).equals(new Order(product, 0))) {
                int amount = ((Order)productsList.elementAt(i)).getAmount();
                if (amount == 1)
                    productsList.removeElementAt(i);
                else
                    ((Order)productsList.elementAt(i)).setAmount(amount - 1);
                return amount - 1;
            }
        return -1;
    }
    
    public static boolean sendOrder(String address, int type, MobiCarta mbc) {
        String data = xmlOrdersBuilder(mbc);
        if (!data.equals("")) {
            try {
                BluetoothClient bc = BluetoothClient.getBluetoothClient();
                bc.sendData(address, data, type, mbc);
                return true;
            }
            catch (BluetoothStateException ex) {
                mbc.showAlert("Error en la conexión Bluetooth", ex.getMessage(), AlertType.ERROR);
                return false;
            }
        }
        return false;
    }
    
    public static boolean billRequest(String address, MobiCarta mbc) {
        productsList = new Vector();
        productsList.addElement(new Order("Solicitud de facturacion", 0));
        return sendOrder(address, 2, mbc);
    }
    
    private static String xmlOrdersBuilder(MobiCarta mbc) {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
        xml += "<ClientOrder>\n";
        if (productsList != null) {
            xml += "\t<Client dni=\"" + ProfileManager.loadClient(mbc).getDNI() + "\"/>\n";
            xml += "\t<Products>\n";
            for (int i = 0; i < productsList.size(); i++)
                xml += "\t\t<Product name=\"" + 
                        ((Order)productsList.elementAt(i)).getProduct() + "\" amount=\"" +
                        ((Order)productsList.elementAt(i)).getAmount() + "\"/>\n";
            xml += "\t</Products>\n";
        }
        xml += "</ClientOrder>\n";
        return xml;
    }
}