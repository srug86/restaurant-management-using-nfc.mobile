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
public class RecommendationManager {
    public static Recommendation recommendation = new Recommendation();
    
    // Directorio donde se guardan las recomendaciones recibidas
    private static String path = "file:///e:/mobiCarta/";
    // Nombre del archivo que contiene las recomendaciones
    private static String recFile = "recommendations.xml";
    
    // Captura la recomendación recibida
    public static void catchRecommendation(String xml, MobiCarta mbc) {
        FileIO.createFile(path, recFile, xml);
        xmlRecommendationDecoder(mbc);
        mbc.getSItemOpening().setText(recommendation.getOpening());
    }
    
    // Carga las recomendaciones almacenadas
    public static void loadRecommendation(MobiCarta mbc) {
        xmlRecommendationDecoder(mbc);
        composeRecommendation(mbc);
    }
    
    // Muestra la promoción del producto seleccionado
    public static String getPromotion(String product) {
        String prom = "";
        for (int i = 0; i < recommendation.getPromotional().size(); i++) {
            RecProduct rp = ((RecProduct)(recommendation.getPromotional().elementAt(i)));
            if (rp.getName().equals(product))
                prom = rp.getDiscount() * 100 + "% desc. " + rp.getDiscountedUnit() + "ª unidad";
        }
        return prom;
    }
    
    // Confecciona la vista de la pantalla que muestra las recomendaciones
    private static void composeRecommendation(MobiCarta mbc) {
        if (recommendation.getPromotional().isEmpty() && recommendation.getRecommended().isEmpty() &&
                recommendation.getUsually().isEmpty())
        {
            mbc.getRecommendations().append(new StringItem("", "No hay ninguna recomendación para usted."));
            mbc.getRecTicker().setString("Todavía no hay recomendaciones para usted.");
        }
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
                mbc.getRecTicker().setString("* PROMOCIONES:: ");
                for (int i = 0; i < recommendation.getPromotional().size(); i++) {
                    RecProduct rp = (RecProduct)recommendation.getPromotional().elementAt(i);
                    mbc.getRecommendations().append(new StringItem(rp.getName(),
                            String.valueOf(String.valueOf(rp.getDiscount() * 100) + "% de desc. en la " + 
                            String.valueOf(rp.getDiscountedUnit()) + "ª unidad.")));
                    mbc.getRecTicker().setString(mbc.getRecTicker().getString() + rp.getName() + ": " +
                            String.valueOf(String.valueOf(rp.getDiscount() * 100) + "% desc. la " +
                            String.valueOf(rp.getDiscountedUnit()) + "ª unidad. / "));
                }
            }
            if (!recommendation.getRecommended().isEmpty()) {
                StringItem si = new StringItem("", "Productos recomendados");
                si.setFont(mbc.getFont());
                mbc.getRecommendations().append(si);
                mbc.getRecTicker().setString(mbc.getRecTicker().getString() + "* RECOMENDACIONES:: ");
                for (int i = 0; i < recommendation.getRecommended().size(); i++) {
                    RecProduct rp = (RecProduct)recommendation.getRecommended().elementAt(i);
                    mbc.getRecommendations().append(new StringItem(rp.getName(), "(" +
                            rp.getCategory() + ")"));
                    mbc.getRecTicker().setString(mbc.getRecTicker().getString() + rp.getName() + " (" +
                            rp.getCategory() + "). / ");
                }
            }
        }
    }
    
    // Decodifica el XML de las recomendaciones para el cliente
    private static void xmlRecommendationDecoder(MobiCarta mbc) {
        KXmlParser parser = new KXmlParser();
        Vector usually = new Vector();
        Vector promotional = new Vector();
        Vector recommended = new Vector();
        recommendation.setUsually(usually);
        recommendation.setPromotional(promotional);
        recommendation.setRecommended(recommended);
        RecProduct product = new RecProduct();
        try {
            // Las recomendaciones se cargan del archivo generado con anterioridad
            FileConnection fileCon = (FileConnection)Connector.open(path + recFile, Connector.READ);
            if (fileCon.exists()) {
                InputStreamReader is = new InputStreamReader(fileCon.openInputStream());
                parser.setInput(is);
                parser.nextTag();
                parser.require(XmlPullParser.START_TAG, null, "Recommendations");
                while (parser.next() != XmlPullParser.END_DOCUMENT) {
                    if (parser.getEventType() == XmlPullParser.START_TAG) {
                        if (parser.getName().equals("Opening"))
                            recommendation.setOpening(parser.nextText());
                        else if (parser.getName().equals("UCategory")) {
                            product = new RecProduct();
                            usually.addElement(product);
                            product.setCategory(parser.getAttributeValue(0));
                        }
                        else if (parser.getName().equals("UProduct"))
                            product.setName(parser.nextText());
                        else if (parser.getName().equals("UTimes"))
                            product.setTimes(Integer.parseInt(parser.nextText()));
                        else if (parser.getName().equals("PProduct")) {
                            product = new RecProduct();
                            promotional.addElement(product);
                            product.setName(parser.getAttributeValue(0));
                        }
                        else if (parser.getName().equals("PDiscount"))
                            product.setDiscount(Double.parseDouble(parser.nextText()));
                        else if (parser.getName().equals("PUnits"))
                            product.setDiscountedUnit(Integer.parseInt(parser.nextText()));
                        else if (parser.getName().equals("RProduct")) {
                            product = new RecProduct();
                            recommended.addElement(product);
                            product.setName(parser.getAttributeValue(0));
                        }
                        else if (parser.getName().equals("RCategory"))
                            product.setCategory(parser.nextText());
                    }
                }
                is.close();
            }
            fileCon.close();
        } catch (XmlPullParserException ex) {
            mbc.showAlert("Error al analizar mensaje", ex.toString(), AlertType.ERROR);
        } catch (IOException ex) {
            mbc.showAlert("Error de entrada/salida", ex.toString(), AlertType.ERROR);
        }
    }
}
