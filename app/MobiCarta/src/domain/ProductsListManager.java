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
    // Vector de productos del pedido
    public static Vector productsList = new Vector();
    
    // Añade un producto a la lista de productos del pedido
    public static String addProduct(String product) {
        String prom = "";
        if (!(prom = RecommendationManager.getPromotion(product)).equals(""))
            prom = "\t\t(" + prom + ")";    // Informa de la promoción del producto (si la tuviera)
        for (int i = 0; i < productsList.size(); i++)
            if (productsList.elementAt(i).equals(new Order(product, 0))) {
                ((Order)productsList.elementAt(i)).setAmount(((Order)productsList.
                        elementAt(i)).getAmount() + 1);
                return ((Order)productsList.elementAt(i)).getAmount() + " " + prom;
            }
        productsList.addElement(new Order(product, 1));
        return 1 + " " + prom;
    }
    
    // Elimina una unidad del producto 'product' de la lista de productos
    public static int removeProduct(String product) {
        for (int i = 0; i < productsList.size(); i++)
            if (productsList.elementAt(i).equals(new Order(product, 0))) {
                int amount = ((Order)productsList.elementAt(i)).getAmount();
                if (amount == 1)    // Si sólo había un producto de ese tipo
                    productsList.removeElementAt(i);    // elimina el producto de la lista
                else    // si no
                    ((Order)productsList.elementAt(i)).setAmount(amount - 1);
                return amount - 1;  // decrementa el número total de productos de ese tipo
            }
        return -1;
    }
    
    // Envía el pedido elaborado o la solicitud de facturación
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
    
    // Crea una solicitud de facturación
    public static boolean billRequest(String address, MobiCarta mbc) {
        productsList = new Vector();
        productsList.addElement(new Order("Solicitud de facturacion", 0));
        return sendOrder(address, 2, mbc);
    }
    
    // Codifica un XML con los datos del pedido
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