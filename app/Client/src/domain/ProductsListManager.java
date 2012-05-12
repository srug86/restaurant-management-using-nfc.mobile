/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.Vector;

/**
 *
 * @author Sergio
 */
public class ProductsListManager {
    public static Vector productsList = new Vector();
    
    public static int addProduct(String product) {
        for (int i = 0; i < productsList.size(); i++)
            if (productsList.elementAt(i).equals(new Order(product, 0))) {
                ((Order)productsList.elementAt(i)).setAmount(((Order)productsList.
                        elementAt(i)).getAmount() + 1);
                return ((Order)productsList.elementAt(i)).getAmount();
            }
        productsList.addElement(new Order(product, 1));
        return 1;
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
    
    public static boolean sendOrder(String btAddress) {
        return true;
    }
    
    private static String xmlOrdersBuilder() {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
        xml += "<ClientOrder>\n";
        if (productsList != null) {
            xml += "\t<Client dni=\"" + ProfileManager.client.getDNI() + "\"/>\n";
            xml += "\t<Products>\n";
            for (int i = 0; i < productsList.size(); i++)
                xml += "\t\t<Product name=\"" + 
                        ((Order)productsList.elementAt(i)).getProduct() + "\" amount=\"" +
                        ((Order)productsList.elementAt(i)).getAmount() + "\"/>\n";
            xml += "\t</Products>\n";
        }
        xml += "</ClientOrder>";
        return xml;
    }
}