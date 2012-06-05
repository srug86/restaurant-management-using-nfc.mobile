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
public class Recommendation {
    private String opening;
    private Vector usually, promotional, recommended;
    
    public Recommendation() {
        this.opening = "";
        this.usually = new Vector();
        this.promotional = new Vector();
        this.recommended = new Vector();
    }
    
    public String getOpening() {
        return this.opening;
    }
    
    public void setOpening(String o) {
        this.opening = o;
    }
    
    public Vector getUsually() {
        return this.usually;
    }
    
    public void setUsually(Vector u) {
        this.usually = u;
    }
    
    public Vector getPromotional() {
        return this.promotional;
    }
    
    public void setPromotional(Vector p) {
        this.promotional = p;
    }
    
    public Vector getRecommended() {
        return this.recommended;
    }
    
    public void setRecommended(Vector r) {
        this.recommended = r;
    }
}
