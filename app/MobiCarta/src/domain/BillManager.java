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
import org.kxml2.io.KXmlParser;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import presentation.MobiCarta;

/**
 *
 * @author Sergio
 */
public class BillManager {
    public static Bill bill = new Bill();
    
    public static void catchBill(String xml, MobiCarta mbc) {
        xmlBillDecoder(xml);
        composeBill(mbc);
    }
    
    private static void composeBill(MobiCarta mbc) {
        StringItem si = new StringItem("Restaurante", bill.getRestaurant());
        si.setFont(mbc.getFont());
        mbc.getBill().append(si);
        mbc.getBill().append(new StringItem("Fecha", bill.getDate()));
        mbc.getBill().append(new StringItem("Mesa", String.valueOf(bill.getTable())));
        si = new StringItem("Descrip. / U. Pr(�) Des(%) Imp(�)", "Pedidos:");
        si.setFont(mbc.getFont());
        mbc.getBill().append(si);
        Vector orders = bill.getOrders();
        for (int i = 0; i < orders.size(); i++) {
            BillItem bi = (BillItem)orders.elementAt(i);
            mbc.getBill().append(new StringItem("- " + bi.getProduct(), String.valueOf(bi.getAmount()) +
                    "  " + String.valueOf(bi.getPrice()) + "  " + String.valueOf(bi.getDiscount()) + 
                            "  " + String.valueOf(bi.getTotal())));
        }
        mbc.getBill().append(new StringItem("Subtotal:", String.valueOf(bill.getSubtotal()) + " �"));
        mbc.getBill().append(new StringItem("Descuento:", String.valueOf(bill.getDiscount()) + "%"));
        mbc.getBill().append(new StringItem("Base impositiva:", String.valueOf(bill.getTaxBase()) + " �"));
        mbc.getBill().append(new StringItem("IVA:", String.valueOf(bill.getIva()) + "%"));
        si = new StringItem("Total:", String.valueOf(bill.getTotal()) + " �");
        si.setFont(mbc.getFont());
        mbc.getBill().append(si);
    }
    
    private static void xmlBillDecoder(String xml) {
        KXmlParser parser = new KXmlParser();
        try {
            InputStream ist = new ByteArrayInputStream(xml.getBytes("UTF-8"));
            InputStreamReader is = new InputStreamReader(ist);
            parser.setInput(is);
            parser.nextTag();
            parser.require(XmlPullParser.START_TAG, null, "Bill");
            while (parser.next() != XmlPullParser.END_DOCUMENT) {
                if (parser.getEventType() == XmlPullParser.START_TAG) {
                    if (parser.getName().equals("Company"))
                        bill.setRestaurant(parser.getAttributeValue(0));
                    else if (parser.getName().equals("Date"))
                        bill.setDate(parser.nextText());
                    else if (parser.getName().equals("Table"))
                        bill.setTable(Integer.parseInt(parser.nextText()));
                    else if (parser.getName().equals("Orders")) {
                        BillItem bi = new BillItem();
                        Vector orders = new Vector();
                        while (parser.next() != XmlPullParser.END_DOCUMENT) {
                            if (parser.getEventType() == XmlPullParser.START_TAG) {
                                if (parser.getName().equals("Order")) {
                                    bi = new BillItem();
                                    orders.addElement(bi);
                                    bi.setId(Integer.parseInt(parser.getAttributeValue(0)));
                                }
                                else if (parser.getName().equals("Product"))
                                    bi.setProduct(parser.nextText());
                                else if (parser.getName().equals("Amount"))
                                    bi.setAmount(Integer.parseInt(parser.nextText()));
                                else if (parser.getName().equals("Price"))
                                    bi.setPrice(Double.parseDouble(parser.nextText()));
                                else if (parser.getName().equals("Discount"))
                                    bi.setDiscount(Double.parseDouble(parser.nextText()));
                                else if (parser.getName().equals("Total"))
                                    bi.setTotal(Double.parseDouble(parser.nextText()));
                                else break;
                            }
                        }
                        bill.setOrders(orders);
                    }
                    else if (parser.getName().equals("Subtotal"))
                        bill.setSubtotal(Double.parseDouble(parser.nextText()));
                    else if (parser.getName().equals("Discount"))
                        bill.setDiscount(Double.parseDouble(parser.nextText()));
                    else if (parser.getName().equals("TaxBase"))
                        bill.setTaxBase(Double.parseDouble(parser.nextText()));
                    else if (parser.getName().equals("IVA"))
                        bill.setIva(Double.parseDouble(parser.nextText()));
                    else if (parser.getName().equals("Quote"))
                        bill.setQuote(Double.parseDouble(parser.nextText()));
                    else if (parser.getName().equals("Total"))
                        bill.setTotal(Double.parseDouble(parser.nextText()));
                }
            }
        } catch (XmlPullParserException ex) {
        } catch (UnsupportedEncodingException ex) {
        } catch (IOException ex) {
        }
    }
}