/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

/**
 *
 * @author Sergio
 */
public class Order {
    /* Atributos del objeto */
    private String product;
    private int amount;
    
    // Método constructor
    public Order(String product, int amount) {
        this.product = product;
        this.amount = amount;
    }
    
    /* 'get's y 'set's de los atributos del objeto */
    public String getProduct() {
        return this.product;
    }
    
    public void setProduct(String p) {
        this.product = p;
    }
    
    public int getAmount() {
        return this.amount;
    }
    
    public void setAmount(int a) {
        this.amount = a;
    }
    
    // Dos pedidos son iguales si tienen el mismo producto
    public boolean equals(Object o) {
        if (!(o instanceof Order)) return false;
        if (o == null) return false;
        Order or = (Order)o;
        if (this.product.equals(or.product)) return true;
        return false;
    }
}
