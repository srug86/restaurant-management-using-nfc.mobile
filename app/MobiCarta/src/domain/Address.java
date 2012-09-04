/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

/**
 *
 * @author Sergio
 */
public class Address {
    /* Atributos del objeto */
    private String street, number, town, state;
    private int zipCode;
    
    /* Métodos constructores */
    public Address() {
        this.street = this.number = this.town = this.state = "";
    }
    
    public Address(String street, String number, int zipCode, String town, String state) {
        this.street = street;
        this.number = number;
        this.zipCode = zipCode;
        this.town = town;
        this.state = state;
    }
    
    /* 'get's y 'set's  de los atributos del objeto */
    public String getStreet() {
        return this.street;
    }
    
    public void setStreet(String s) {
        this.street = s;
    }
    
    public String getNumber() {
        return this.number;
    }
    
    public void setNumber(String n) {
        this.number = n;
    }
    
    public int getZipCode() {
        return this.zipCode;
    }
    
    public void setZipCode(int zc) {
        this.zipCode = zc;
    }
    
    public String getTown() {
        return this.town;
    }
    
    public void setTown(String t) {
        this.town = t;
    }
    
    public String getState() {
        return this.state;
    }
    
    public void setState(String s) {
        this.state = s;
    }
}
