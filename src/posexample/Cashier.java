/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package posexample;

import java.util.*;
import java.io.*;


/**
 *
 * @author KwabenaEpic
 */
public class Cashier extends Observable implements Comparable, Serializable{
    
    private static int nextCashierNumber = 0;
    
    private String name = "name";
    
    private String password = "password";
    
    private int number = 0;
    
    private Collection sessions = new TreeSet();
    
    public Cashier(){
        this("cashie's name", "password");
    }
    
    public Cashier(String name, String password){
        this.number = ++nextCashierNumber;
        this.name = name;
        this.password = password;
    }
    
    public String getName(){
       return name;
    }
    
    public void setName(String n){
        name = n;
    }
    
    public String toString (){
        return name + "#" + number;
    }//getName
    
    public int getNumber(){
        return number;
    }//getNumber
    
    public void setNumber(int n){
        number = n;
    }//setNumber
    
    public boolean verifyPassword(String password){
        if(this.password.equals(password)){
            return true;
        }//if
        return false;
    }//verifyPassword
    
    public void addSession(Session s){    
        sessions.add(s);
    }//addSesion
    
    
    

    @Override
    public int compareTo(Object obj) {
        if ((obj !=null ) && (obj instanceof Cashier)){
            Cashier c = (Cashier)obj;
            if (getName().compareTo(c.getName()) !=0){
                return getName().compareTo(c.getName());
            }
            else {
                return getNumber() -c.getNumber();
            }
        }
        throw new ClassCastException("" + obj + " is not a cashier"); //To change body of generated methods, choose Tools | Templates.
    }
    
}
