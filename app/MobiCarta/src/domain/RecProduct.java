/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

/**
 *
 * @author Sergio
 */
public class RecProduct {
    private String name, category;
    private int times, discountedUnit;
    private double discount;
    
    public RecProduct() {
        this.name = this.category = "";
        this.times = this.discountedUnit = 0;
        this.discount = 0.0;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(String n) {
        this.name = n;
    }
    
    public String getCategory() {
        return this.category;
    }
    
    public void setCategory(String c) {
        this.category = c;
    }
    
    public int getTimes() {
        return this.times;
    }
    
    public void setTimes(int t) {
        this.times = t;
    }
    
    public int getDiscountedUnit() {
        return this.discountedUnit;
    }
    
    public void setDiscountedUnit(int du) {
        this.discountedUnit = du;
    }
    
    public double getDiscount() {
        return this.discount;
    }
    
    public void setDiscount(double d) {
        this.discount = d;
    }
    
    public boolean equals(Object o) {
        if (!(o instanceof RecProduct)) return false;
        if (o == null) return false;
        RecProduct rp = (RecProduct)o;
        if (this.name.equals(rp.name)) return true;
        return false;
    }
}
