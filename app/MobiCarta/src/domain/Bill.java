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
public class Bill {
    private String restaurant, date;
    private Vector orders;
    private int table;
    private double subtotal, discount, taxBase, iva, quote, total;
    
    public Bill() {
        this.restaurant = this.date = "";
        this.orders = new Vector();
        this.table = 0;
        this.subtotal = this.discount = this.taxBase = this.iva = 
                this.quote = this.total = 0.0;
    }
    
    public String getRestaurant() {
        return this.restaurant;
    }
    
    public void setRestaurant(String r) {
        this.restaurant = r;
    }
    
    public String getDate() {
        return this.date;
    }
    
    public void setDate(String d) {
        this.date = d;
    }
    
    public Vector getOrders() {
        return this.orders;
    }
    
    public void setOrders(Vector o) {
        this.orders = o;
    }
    
    public double getSubtotal() {
        return this.subtotal;
    }
    
    public void setSubtotal(double s) {
        this.subtotal = s;
    }
    
    public double getDiscount() {
        return this.discount;
    }
    
    public void setDiscount(double d) {
        this.discount = d;
    }
    
    public double getTaxBase() {
        return this.taxBase;
    }
    
    public void setTaxBase(double t) {
        this.taxBase = t;
    }
    
    public double getIva() {
        return this.iva;
    }
    
    public void setIva(double i) {
        this.iva = i;
    }
    
    public double getQuote() {
        return this.quote;
    }
    
    public void setQuote(double q) {
        this.quote = q;
    }
    
    public double getTotal() {
        return this.total;
    }
    
    public void setTotal(double t) {
        this.total = t;
    }
}
