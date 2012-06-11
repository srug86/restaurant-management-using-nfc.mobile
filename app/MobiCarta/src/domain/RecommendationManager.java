/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;
import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;
import javax.microedition.lcdui.StringItem;
import org.kxml2.io.KXmlParser;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import presentation.MobiCarta;

/**
 *
 * @author Sergio
 */
public class RecommendationManager {
    public static Recommendation recommendation = new Recommendation();
    
    public static String path = "file:///e:/mobiCarta/recommendations.xml";
    
    public static void catchRecommendation(String xml, MobiCarta mbc) {
        FileIO.createFile(path, xml);
        xmlRecommendationDecoder(path);
        mbc.getSItemOpening().setText(recommendation.getOpening());
    }
    
    public static void loadRecommendation(MobiCarta mbc) {
        xmlRecommendationDecoder(path);
        composeRecommendation(mbc);
    }
    
    private static void composeRecommendation(MobiCarta mbc) {
        if (recommendation.getPromotional().isEmpty() && recommendation.getRecommended().isEmpty() &&
                recommendation.getUsually().isEmpty())
            mbc.getRecommendations().append(new StringItem("", "No hay ninguna recomendación para usted."));
        else {
            if (!recommendation.getUsually().isEmpty()) {
                StringItem si = new StringItem("", "Productos más consumidos");
                si.setFont(mbc.getFont());
                mbc.getRecommendations().append(si);
                for (int i = 0; i < recommendation.getUsually().size(); i++) {
                    RecProduct rp = (RecProduct)recommendation.getUsually().elementAt(i);
                    mbc.getRecommendations().append(new StringItem(rp.getName() + " (" + 
                            rp.getCategory() + ")", String.valueOf(rp.getTimes() + " veces")));
                }
            }
            if (!recommendation.getPromotional().isEmpty()) {
                StringItem si = new StringItem("", "Productos con descuento");
                si.setFont(mbc.getFont());
                mbc.getRecommendations().append(si);
                for (int i = 0; i < recommendation.getPromotional().size(); i++) {
                    RecProduct rp = (RecProduct)recommendation.getPromotional().elementAt(i);
                    mbc.getRecommendations().append(new StringItem(rp.getName(),
                            String.valueOf(String.valueOf(rp.getDiscount() * 100) + "% de desc. en la " + 
                            String.valueOf(rp.getDiscountedUnit()) + "ª unidad.")));
                }
            }
            if (!recommendation.getRecommended().isEmpty()) {
                StringItem si = new StringItem("", "Productos recomendados");
                si.setFont(mbc.getFont());
                mbc.getRecommendations().append(si);
                for (int i = 0; i < recommendation.getRecommended().size(); i++) {
                    RecProduct rp = (RecProduct)recommendation.getRecommended().elementAt(i);
                    mbc.getRecommendations().append(new StringItem(rp.getName(), "(" +
                            rp.getCategory() + ")"));
                }
            }
        }
    }
    
    private static void xmlRecommendationDecoder(String file) {
        KXmlParser parser = new KXmlParser();
        try {
            FileConnection fileCon = (FileConnection)Connector.open(file, Connector.READ);
            InputStreamReader is = new InputStreamReader(fileCon.openInputStream());
            parser.setInput(is);
            parser.nextTag();
            parser.require(XmlPullParser.START_TAG, null, "Recommendations");
            while (parser.next() != XmlPullParser.END_DOCUMENT) {
                if (parser.getEventType() == XmlPullParser.START_TAG) {
                    if (parser.getName().equals("Opening"))
                        recommendation.setOpening(parser.nextText());
                    else if (parser.getName().equals("Usually")) {
                        RecProduct product = new RecProduct();
                        Vector usually = new Vector();
                        while (parser.next() != XmlPullParser.END_DOCUMENT) {
                            if (parser.getEventType() == XmlPullParser.START_TAG) {
                                if (parser.getName().equals("Category")) {
                                    product = new RecProduct();
                                    usually.addElement(product);
                                    product.setCategory(parser.getAttributeValue(0));
                                }
                                else if (parser.getName().equals("Product"))
                                    product.setName(parser.nextText());
                                else if (parser.getName().equals("Times"))
                                    product.setTimes(Integer.parseInt(parser.nextText()));
                                else break;
                            }
                        }
                        recommendation.setUsually(usually);
                        Vector promotional = new Vector();
                        while (parser.next() != XmlPullParser.END_DOCUMENT) {
                            if (parser.getEventType() == XmlPullParser.START_TAG) {
                                if (parser.getName().equals("Product")) {
                                    product = new RecProduct();
                                    promotional.addElement(product);
                                    product.setName(parser.getAttributeValue(0));
                                }
                                else if (parser.getName().equals("Discount"))
                                    product.setDiscount(Double.parseDouble(parser.nextText()));
                                else if (parser.getName().equals("Units"))
                                    product.setDiscountedUnit(Integer.parseInt(parser.nextText()));
                                else break;
                            }
                        }
                        recommendation.setPromotional(promotional);
                        Vector recommended = new Vector();
                        while (parser.next() != XmlPullParser.END_DOCUMENT) {
                            if (parser.getEventType() == XmlPullParser.START_TAG) {
                                if (parser.getName().equals("Product")) {
                                    product = new RecProduct();
                                    recommended.addElement(product);
                                    product.setName(parser.getAttributeValue(0));
                                    product.setCategory(parser.getAttributeValue(1));
                                }
                                else break;
                            }
                        }
                        recommendation.setRecommended(recommended);
                    }
                }
            }
        } catch (XmlPullParserException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
