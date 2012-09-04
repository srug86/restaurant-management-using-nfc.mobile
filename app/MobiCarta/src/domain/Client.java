/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

/**
 *
 * @author Sergio
 */
public class Client {
    /* Atributos del objeto */
    private String dni, name, surname;
    
    private Address address;
    
    /* Métodos constructores */
    public Client() { 
        this.dni = this.name = this.surname = "";
        this.address = new Address();
    }
    
    public Client(String dni, String name, String surname, Address address) {
        this.dni = dni;
        this.name = name;
        this.surname = surname;
        this.address = address;
    }
    
    /* 'get's y 'set's de los atributos del objeto */
    public String getDNI() {
        return this.dni;
    }
    
    public void setDNI(String dni) {
        this.dni = dni;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(String n) {
        this.name = n;
    }
    
    public String getSurname() {
        return this.surname;
    }
    
    public void setSurname(String s) {
        this.surname = s;
    }
    
    public Address getAddress() {
        return this.address;
    }
    
    public void setAddress(Address a) {
        this.address = a;
    }
}
