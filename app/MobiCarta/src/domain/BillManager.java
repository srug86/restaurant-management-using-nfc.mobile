/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Vector;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.StringItem;
import javax.microedition.lcdui.Ticker;
import org.kxml2.io.KXmlParser;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import presentation.MobiCarta;

/**
 *
 * @author Sergio
 */
public class BillManager {
    
    // Captura la factura recibida
    public static void catchBill(String xml, MobiCarta mbc) {
        Bill bill = xmlBillDecoder(mbc, xml);
        composeBill(bill, mbc);
    }
    
    // Confecciona la vista de la pantalla que muestra la factura
    private static void composeBill(Bill bill, MobiCarta mbc) {
        mbc.getDisplay().getCurrent().setTicker(new Ticker("iniciando composición de factura..."));
        StringItem si = new StringItem("Restaurante", bill.getRestaurant());
        si.setFont(mbc.getFont());
        mbc.getBill().append(si);
        mbc.getBill().append(new StringItem("Fecha", bill.getDate()));
        mbc.getBill().append(new StringItem("Mesa", String.valueOf(bill.getTable())));
        mbc.getBill().append(new StringItem("Pedidos:", "Descrip. / U. Pr(€) Des(€) Imp(€)"));
        Vector orders = bill.getOrders();
        mbc.getDisplay().getCurrent().setTicker(new Ticker("añadiendo los productos..."));
        for (int i = 0; i < orders.size(); i++) {
            BillItem bi = (BillItem)orders.elementAt(i);
            mbc.getBill().append(new StringItem("- " + bi.getProduct(), String.valueOf(bi.getAmount()) + 
                    "  " + String.valueOf(bi.getPrice()) + "  " + String.valueOf(bi.getDiscount()) + 
                    "  " + String.valueOf(bi.getTotal())));
        }
        mbc.getDisplay().getCurrent().setTicker(new Ticker("calculando el total..."));
        mbc.getBill().append(new StringItem("Subtotal:", String.valueOf(bill.getSubtotal()) + " €"));
        mbc.getBill().append(new StringItem("Descuento:", String.valueOf(bill.getDiscount()) + "%"));
        mbc.getBill().append(new StringItem("Base impositiva:", String.valueOf(bill.getTaxBase()) + " €"));
        mbc.getBill().append(new StringItem("IVA:", String.valueOf(bill.getIva()) + "%"));
        si = new StringItem("Total:", String.valueOf(bill.getTotal()) + " €");
        si.setFont(mbc.getFont());
        mbc.getBill().append(si);
    }
    
    // Decodifica el XML con los datos de la factura
    private static Bill xmlBillDecoder(MobiCarta mbc, String xml) {
        Vector orders = new Vector();
        Bill bill = new Bill();
        bill.setOrders(orders);
        BillItem bi = new BillItem();
        KXmlParser parser = new KXmlParser();
        try {            
            InputStream ist = new ByteArrayInputStream(xml.getBytes("ASCII"));
            InputStreamReader is = new InputStreamReader(ist);
            parser.setInput(is);
            parser.nextTag();
            parser.require(XmlPullParser.START_TAG, null, "Bill");
            while (parser.next() != XmlPullParser.END_DOCUMENT) {
                if (parser.getEventType() == XmlPullParser.START_TAG) {
                    if (parser.getName().equals("Company"))
                        bill.setRestaurant(parser.nextText());
                    else if (parser.getName().equals("Date"))
                        bill.setDate(parser.nextText());
                    else if (parser.getName().equals("Table"))
                        bill.setTable(Integer.parseInt(parser.nextText()));
                    else if (parser.getName().equals("Order")) {
                        bi = new BillItem();
                        orders.addElement(bi);
                        bi.setProduct(parser.getAttributeValue(0));
                    }
                    else if (parser.getName().equals("Amount"))
                        bi.setAmount(Integer.parseInt(parser.nextText()));
                    else if (parser.getName().equals("Price"))
                        bi.setPrice(Double.parseDouble(parser.nextText()));
                    else if (parser.getName().equals("PDiscount"))
                        bi.setDiscount(Double.parseDouble(parser.nextText()));
                    else if (parser.getName().equals("PTotal"))
                        bi.setTotal(Double.parseDouble(parser.nextText()));
                    else if (parser.getName().equals("Subtotal"))
                        bill.setSubtotal(Double.parseDouble(parser.nextText()));
                    else if (parser.getName().equals("BDiscount"))
                        bill.setDiscount(Double.parseDouble(parser.nextText()));
                    else if (parser.getName().equals("TaxBase"))
                        bill.setTaxBase(Double.parseDouble(parser.nextText()));
                    else if (parser.getName().equals("IVA"))
                        bill.setIva(Double.parseDouble(parser.nextText()));
                    else if (parser.getName().equals("Quote"))
                        bill.setQuote(Double.parseDouble(parser.nextText()));
                    else if (parser.getName().equals("BTotal"))
                        bill.setTotal(Double.parseDouble(parser.nextText()));
                }
            }
            is.close();
            ist.close();
        } catch (XmlPullParserException ex) {
             mbc.showAlert("Error al analizar mensaje", ex.toString(), AlertType.ERROR);
        } catch (UnsupportedEncodingException ex) {
             mbc.showAlert("Error de codificación", ex.toString(), AlertType.ERROR);
        } catch (IOException ex) {
             mbc.showAlert("Error de entrada/salida", ex.toString(), AlertType.ERROR);
        }
        return bill;
    }
}
