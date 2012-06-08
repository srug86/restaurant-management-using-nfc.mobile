/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

/**
 *
 * @author Sergio
 */
public class BillItem {
    private String product;
    private int id, amount;
    private double price, discount, total;
    
    public BillItem() {
        this.product = "";
        this.id = 0;
        this.amount = 1;
        this.price = this.discount = this.total = 0.0;
    }
    
    public String getProduct() {
        return this.product;
    }
    
    public void setProduct(String p) {
        this.product = p;
    }
    
    public int getId() {
        return this.id;
    }
    
    public void setId(int i) {
        this.id = i;
    }
    
    public int getAmount() {
        return this.amount;
    }
    
    public void setAmount(int a) {
        this.amount = a;
    }
    
    public double getPrice() {
        return this.price;
    }
    
    public void setPrice(double p) {
        this.price = p;
    }
    
    public double getDiscount() {
        return this.discount;
    }
    
    public void setDiscount(double d) {
        this.discount = d;
    }
    
    public double getTotal() {
        return this.total;
    }
    
    public void setTotal(double t) {
        this.total = t;
    }    
}
