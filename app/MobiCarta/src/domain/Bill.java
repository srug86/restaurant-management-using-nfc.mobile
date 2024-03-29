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
    /* Atributos del objeto */
    private String restaurant, date;
    private Vector orders;
    private int table;
    private double subtotal, discount, taxBase, iva, quote, total;
    
    // M�todo constructor
    public Bill() {
        this.restaurant = this.date = "";
        this.orders = new Vector();
        this.table = 0;
        this.subtotal = this.discount = this.taxBase = this.iva = 
                this.quote = this.total = 0.0;
    }
    
    /* 'get's y 'set's de los atributos del objeto */
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
    
    public int getTable() {
        return this.table;
    }
    
    public void setTable(int t) {
        this.table = t;
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
