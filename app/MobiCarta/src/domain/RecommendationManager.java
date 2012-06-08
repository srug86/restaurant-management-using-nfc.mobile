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
